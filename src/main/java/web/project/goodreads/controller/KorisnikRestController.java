package web.project.goodreads.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.project.goodreads.dto.*;
import web.project.goodreads.entity.*;
import web.project.goodreads.service.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class KorisnikRestController {
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private PolicaService policaService;
    @Autowired
    private AutorService autorService;

    @GetMapping("/korisnik/{id}")
    public ResponseEntity<PregledKorisnikaDto> pregledKorisnika(@PathVariable(name = "id") Long id){
        Korisnik k = korisnikService.findOne(id);

        if(k == null){
            return new ResponseEntity("Korisnik ne postoji", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(pregledKorisnikaDto(k));
    }

    @PostMapping("/signin")
    public ResponseEntity<PregledKorisnikaDto> signin(@RequestBody SignInDto signInDto){
        if(signInDto.getIme().isEmpty() || signInDto.getPrezime().isEmpty() || signInDto.getKorisnickoIme().isEmpty() || signInDto.getMejl().isEmpty()
                || signInDto.getLozinka().isEmpty() || signInDto.getPonovljenaLozinka().isEmpty())
            return new ResponseEntity("Uneti neispravni podaci", HttpStatus.BAD_REQUEST);

        if (!signInDto.getLozinka().equals(signInDto.getPonovljenaLozinka()))
            return new ResponseEntity("Lozinke nisu iste", HttpStatus.BAD_REQUEST);

        for (Korisnik k : korisnikService.findAll()){
            if(signInDto.getKorisnickoIme().equals(k.getKorisnickoIme()))
                return new ResponseEntity("Korisnicko ime se vec koristi", HttpStatus.BAD_REQUEST);
            if(signInDto.getMejl().equals(k.getMejl()))
                return new ResponseEntity("Mejl se vec koristi", HttpStatus.BAD_REQUEST);
        }

        Korisnik korisnik = new Korisnik(signInDto.getIme(), signInDto.getPrezime(), signInDto.getKorisnickoIme(), signInDto.getMejl(), signInDto.getLozinka());
        this.korisnikService.save(korisnik);

        Polica p1 = new Polica("Want to read", true, korisnik);
        Polica p2 = new Polica("Currently reading", true, korisnik);
        Polica p3 = new Polica("Read", true, korisnik);
        policaService.save(p1);
        policaService.save(p2);
        policaService.save(p3);

        return ResponseEntity.ok(pregledKorisnikaDto(korisnik));
    }

    @PostMapping("/login")
    public ResponseEntity<PregledKorisnikaDto> login(@RequestBody SignInDto signInDto, HttpSession session){
        if(signInDto.getMejl().isEmpty() || signInDto.getLozinka().isEmpty())
            return new ResponseEntity("Uneti neispravni podaci", HttpStatus.BAD_REQUEST);

        Korisnik korisnik = korisnikService.login(signInDto.getMejl(), signInDto.getLozinka());

        if (korisnik == null)
            return new ResponseEntity("Korisnik ne postoji", HttpStatus.NOT_FOUND);

        session.setAttribute("korisnik", korisnik);

        return ResponseEntity.ok(pregledKorisnikaDto(korisnik));
    }

    private PregledKorisnikaDto pregledKorisnikaDto(Korisnik k){
        Korisnik korisnik = new Korisnik();
        korisnik.setId(k.getId());
        korisnik.setKorisnickoIme(k.getKorisnickoIme());
        korisnik.setProfilnaSlika(k.getProfilnaSlika());
        korisnik.setOpis(k.getOpis());

        Set<Polica> police = new HashSet<>();

        for(Polica p : policaService.findAll(k)){
            Polica polica = new Polica();
            polica.setId(p.getId());
            polica.setNaziv(p.getNaziv());
            police.add(polica);
        }

        return new PregledKorisnikaDto(korisnik, police);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        session.invalidate();
        return new ResponseEntity("Uspesno odjavljivanje", HttpStatus.OK);
    }

    @PutMapping("/korisnik")
    public ResponseEntity<Korisnik> azurirajProfil(@RequestBody AzuriranjeKorisnikaDto azuriranjeKorisnikaDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        if(!azuriranjeKorisnikaDto.getIme().isEmpty())
            korisnik.setIme(azuriranjeKorisnikaDto.getIme());

        if(!azuriranjeKorisnikaDto.getPrezime().isEmpty())
            korisnik.setPrezime(azuriranjeKorisnikaDto.getPrezime());

        if(!azuriranjeKorisnikaDto.getProfilnaSlika().isEmpty())
            korisnik.setProfilnaSlika(azuriranjeKorisnikaDto.getProfilnaSlika());

        if(azuriranjeKorisnikaDto.getDatumRodjenja() != null)
            korisnik.setDatumRodjenja(azuriranjeKorisnikaDto.getDatumRodjenja());

        if(!azuriranjeKorisnikaDto.getOpis().isEmpty())
            korisnik.setOpis(azuriranjeKorisnikaDto.getOpis());

        if(!azuriranjeKorisnikaDto.getLozinka().isEmpty())
            if(!korisnik.getLozinka().equals(azuriranjeKorisnikaDto.getStaraLozinka()))
                return new ResponseEntity("Lozinke se ne podudaraju", HttpStatus.BAD_REQUEST);

        korisnik.setLozinka(azuriranjeKorisnikaDto.getLozinka());

        if(!azuriranjeKorisnikaDto.getMejl().isEmpty())
            korisnik.setMejl(azuriranjeKorisnikaDto.getMejl());

        korisnikService.save(korisnik);

        Korisnik k = new Korisnik();
        k.setId(korisnik.getId());
        k.setIme(korisnik.getIme());
        k.setPrezime(korisnik.getPrezime());
        k.setProfilnaSlika(korisnik.getProfilnaSlika());
        k.setOpis(korisnik.getOpis());
        k.setLozinka(korisnik.getLozinka());
        k.setMejl(korisnik.getMejl());
        k.setDatumRodjenja(korisnik.getDatumRodjenja());

        return ResponseEntity.ok(k);
    }

    @PostMapping("/autor")
    public ResponseEntity<Autor> dodajAutora(@RequestBody AutorDto autorDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null || korisnik.getUloga() != Korisnik.Uloga.ADMINISTRATOR)
            return new ResponseEntity("Nemate adminska prava", HttpStatus.FORBIDDEN);

        Autor autor = new Autor();
        autor.setUloga(Korisnik.Uloga.AUTOR);
        autor.setAktivan(false);
        autor.setIme(autorDto.getIme());
        autor.setPrezime(autorDto.getPrezime());
        autor.setProfilnaSlika(autorDto.getSlika());
        autor.setOpis(autorDto.getOpis());
        autorService.save(autor);

        return ResponseEntity.ok(autor);
    }

    @PutMapping("/autor/{id}")
    public ResponseEntity<Autor> azurirajAutora(@PathVariable(name="id") Long id, @RequestBody AutorDto autorDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null || korisnik.getUloga() != Korisnik.Uloga.ADMINISTRATOR)
            return new ResponseEntity("Nemate adminska prava", HttpStatus.FORBIDDEN);

        Autor autor = autorService.findOne(id);

        if(autor == null || autor.getAktivan())
            return new ResponseEntity("Autor je aktivan/ne postoji", HttpStatus.FORBIDDEN);

        if(!autorDto.getIme().isEmpty())
            autor.setIme(autorDto.getIme());
        if(!autorDto.getPrezime().isEmpty())
            autor.setPrezime(autorDto.getPrezime());
        if(!autorDto.getSlika().isEmpty())
            autor.setProfilnaSlika(autorDto.getSlika());
        if(!autorDto.getOpis().isEmpty())
            autor.setOpis(autorDto.getOpis());

        autorService.save(autor);
        return ResponseEntity.ok(autor);
    }
}
package web.project.goodreads.controller;

import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.project.goodreads.dto.*;
import web.project.goodreads.entity.*;
import web.project.goodreads.service.*;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class KorisnikRestController {
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private PolicaService policaService;
    @Autowired
    private AutorService autorService;
    @Autowired
    private StavkaPoliceService stavkaPoliceService;

    @GetMapping("/korisnik/{id}")
    public ResponseEntity<PregledKorisnikaDto> pregledKorisnika(@PathVariable(name = "id") Long id){
        Korisnik k = korisnikService.findOne(id);

        if(k == null){
            return new ResponseEntity("Korisnik ne postoji", HttpStatus.NOT_FOUND);
        }

        Korisnik korisnik = new Korisnik();
        korisnik.setId(k.getId());
        korisnik.setSessionId(k.getSessionId());
        korisnik.setKorisnickoIme(k.getKorisnickoIme());
        korisnik.setProfilnaSlika(k.getProfilnaSlika());
        korisnik.setIme(k.getIme());
        korisnik.setPrezime(k.getPrezime());
        korisnik.setOpis(k.getOpis());
        korisnik.setUloga(k.getUloga());

        Set<PolicaDto> police = new HashSet<>();

        for(Polica p : policaService.findAll(k)){
            PolicaDto polica = new PolicaDto();
            polica.setId(p.getId());
            polica.setNaziv(p.getNaziv());
            polica.setKnjige(new HashSet<>());

            for(StavkaPolice sp : stavkaPoliceService.findAll(policaService.findOne(p.getId()))){
                if(sp.getKnjiga() != null){
                    Autor autor = new Autor();
                    autor.setIme(sp.getKnjiga().getAutor().getIme());
                    autor.setPrezime(sp.getKnjiga().getAutor().getPrezime());

                    Knjiga knjiga = new Knjiga();
                    knjiga.setId(sp.getKnjiga().getId());
                    knjiga.setNaslov(sp.getKnjiga().getNaslov());
                    knjiga.setSlika(sp.getKnjiga().getSlika());
                    knjiga.setAutor(autor);

                    polica.getKnjige().add(knjiga);
                }
            }

            police.add(polica);
        }

        return ResponseEntity.ok(new PregledKorisnikaDto(korisnik, police));
    }

    @GetMapping("/profil/{sessionId}")
    public ResponseEntity<PregledKorisnikaDto> pregledKorisnika(@PathVariable(name = "sessionId") String sessionId){
        Korisnik k = korisnikService.findBySessionId(sessionId);

        if(k == null){
            return new ResponseEntity("Korisnik ne postoji", HttpStatus.NOT_FOUND);
        }

        Korisnik korisnik = new Korisnik();
        korisnik.setId(k.getId());
        korisnik.setSessionId(k.getSessionId());
        korisnik.setKorisnickoIme(k.getKorisnickoIme());
        korisnik.setProfilnaSlika(k.getProfilnaSlika());
        korisnik.setIme(k.getIme());
        korisnik.setPrezime(k.getPrezime());
        korisnik.setOpis(k.getOpis());
        korisnik.setUloga(k.getUloga());

        Set<PolicaDto> police = new HashSet<>();

        for(Polica p : policaService.findAll(k)){
            PolicaDto polica = new PolicaDto();
            polica.setId(p.getId());
            polica.setPrimarno(p.isPrimarno());
            polica.setNaziv(p.getNaziv());
            polica.setKnjige(new HashSet<>());

            for(StavkaPolice sp : stavkaPoliceService.findAll(policaService.findOne(p.getId()))){
                if(sp.getKnjiga() != null){
                    Autor autor = new Autor();
                    autor.setIme(sp.getKnjiga().getAutor().getIme());
                    autor.setPrezime(sp.getKnjiga().getAutor().getPrezime());

                    Knjiga knjiga = new Knjiga();
                    knjiga.setId(sp.getKnjiga().getId());
                    knjiga.setNaslov(sp.getKnjiga().getNaslov());
                    knjiga.setSlika(sp.getKnjiga().getSlika());
                    knjiga.setAutor(autor);

                    polica.getKnjige().add(knjiga);
                }
            }

            police.add(polica);
        }

        return ResponseEntity.ok(new PregledKorisnikaDto(korisnik, police));
    }

    @PostMapping(value="/signin")
    public ResponseEntity<String> signin(@RequestBody SignInDto signInDto, HttpSession session){
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
        korisnik.setProfilnaSlika("https://cdn-icons-png.flaticon.com/512/85/85615.png");

        session.setAttribute("korisnik", korisnik);

        korisnik.setSessionId(session.getId());
        this.korisnikService.save(korisnik);

        Polica p1 = new Polica("Want to read", true, korisnik);
        Polica p2 = new Polica("Currently reading", true, korisnik);
        Polica p3 = new Polica("Read", true, korisnik);
        policaService.save(p1);
        policaService.save(p2);
        policaService.save(p3);

        return ResponseEntity.ok(session.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody SignInDto signInDto, HttpSession session){
        if(signInDto.getMejl().isEmpty() || signInDto.getLozinka().isEmpty())
            return new ResponseEntity(new StringDto("Uneti neispravni podaci"), HttpStatus.BAD_REQUEST);

        Korisnik korisnik = korisnikService.login(signInDto.getMejl(), signInDto.getLozinka());

        if (korisnik == null)
            return new ResponseEntity("Korisnik ne postoji", HttpStatus.NOT_FOUND);

        session.setAttribute("korisnik", korisnik);
        korisnik.setSessionId(session.getId());
        this.korisnikService.save(korisnik);

        return ResponseEntity.ok(session.getId());
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpSession session, String sessionId){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if(korisnik != null)
            for(Korisnik k : korisnikService.findAll())
                if(k.getSessionId().equals(sessionId)){
                    session.invalidate();
                    korisnik.setSessionId(null);
                }

        return new ResponseEntity("Uspesno odjavljivanje", HttpStatus.OK);
    }

    @PutMapping("/korisnik")
    public ResponseEntity<String> azurirajProfil(@RequestBody AzuriranjeKorisnikaDto azuriranjeKorisnikaDto, String sessionId){
        Korisnik korisnik = korisnikService.findBySessionId(sessionId);

        if (korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        if(azuriranjeKorisnikaDto.getIme() != null)
            korisnik.setIme(azuriranjeKorisnikaDto.getIme());

        if(azuriranjeKorisnikaDto.getPrezime() != null)
            korisnik.setPrezime(azuriranjeKorisnikaDto.getPrezime());

        if(azuriranjeKorisnikaDto.getProfilnaSlika() != null)
            korisnik.setProfilnaSlika(azuriranjeKorisnikaDto.getProfilnaSlika());

        if(azuriranjeKorisnikaDto.getDatumRodjenja() != null)
            korisnik.setDatumRodjenja(azuriranjeKorisnikaDto.getDatumRodjenja());

        if(azuriranjeKorisnikaDto.getOpis() != null)
            korisnik.setOpis(azuriranjeKorisnikaDto.getOpis());

        if(azuriranjeKorisnikaDto.getLozinka() != null && azuriranjeKorisnikaDto.getStaraLozinka() != null)
            if(!korisnik.getLozinka().equals(azuriranjeKorisnikaDto.getStaraLozinka()))
                return new ResponseEntity("To nije trenutna lozinka", HttpStatus.BAD_REQUEST);

        if(azuriranjeKorisnikaDto.getLozinka() != null)
            korisnik.setLozinka(azuriranjeKorisnikaDto.getLozinka());

        for (Korisnik k : korisnikService.findAll())
            if(k.getMejl().equals(azuriranjeKorisnikaDto.getMejl()))
                return new ResponseEntity("Ovaj mejl se vec koristi", HttpStatus.BAD_REQUEST);

        if(azuriranjeKorisnikaDto.getMejl() != null)
            if(azuriranjeKorisnikaDto.getLozinka() != null && azuriranjeKorisnikaDto.getStaraLozinka() != null)
                if(!korisnik.getLozinka().equals(azuriranjeKorisnikaDto.getStaraLozinka()))
                    korisnik.setMejl(azuriranjeKorisnikaDto.getMejl());

        korisnikService.save(korisnik);

        return ResponseEntity.ok("Uspesno izmenjen profil");
    }

    @PostMapping("/autor")
    public ResponseEntity<Autor> dodajAutora(@RequestBody AutorDto autorDto, String sessionId){
        Korisnik korisnik = korisnikService.findBySessionId(sessionId);

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
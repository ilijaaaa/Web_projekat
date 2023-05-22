package web.project.goodreads.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.project.goodreads.dto.*;
import web.project.goodreads.entity.*;
import web.project.goodreads.service.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class KorisnikRestController {
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private PolicaService policaService;
    @Autowired
    private  ZahtevZaAktivacijuService zahtevZaAktivacijuService;

    @GetMapping("/korisnici")
    public ResponseEntity<List<Korisnik>> getKorisnici() { return ResponseEntity.ok(korisnikService.findAll()); }

    @GetMapping("/korisnici/{id}")
    public ResponseEntity<Korisnik> getKorisnik(@PathVariable(name = "id") Long id){
        Korisnik korisnik = korisnikService.findOne(id);

        if(korisnik == null){
            return new ResponseEntity("Korisnik ne postoji", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(korisnik);
    }

    @PostMapping("/zahtev")
    public ResponseEntity<String> zahtev(@RequestBody ZahtevZaAktivacijuDto zahtevDto){

        List<Korisnik> korisnici = korisnikService.findAll();

        for (Korisnik k : korisnici)
            if(zahtevDto.getMejl().equals(k.getMejl()))
                return new ResponseEntity("Zahtev je neuspesan", HttpStatus.BAD_REQUEST);

        ZahtevZaAktivaciju zahtev = new ZahtevZaAktivaciju(zahtevDto.getMejl(), zahtevDto.getTelefon(), zahtevDto.getPoruka());
        this.zahtevZaAktivacijuService.save(zahtev);

        return ResponseEntity.ok("Zahtev uspesno prosledjen");
    }

    @PostMapping("/login")
    public ResponseEntity<Set<Polica>> login(@RequestBody LoginDto loginDto, HttpSession session){
        if(loginDto.getMejl().isEmpty() || loginDto.getLozinka().isEmpty())
            return new ResponseEntity("Uneti neispravni podaci", HttpStatus.BAD_REQUEST);

        Korisnik korisnik = korisnikService.login(loginDto.getMejl(), loginDto.getLozinka());

        if (korisnik == null)
            return new ResponseEntity("Korisnik ne postoji", HttpStatus.NOT_FOUND);

        session.setAttribute("korisnik", korisnik);

        Set<Polica> police = new HashSet<>();

        for (Polica p : policaService.findAll())
            if(p.getKorisnik().getId() == korisnik.getId())
                police.add(p);

        return ResponseEntity.ok(police);
    }

    @PostMapping("/signin")
    public ResponseEntity<Korisnik> signin(@RequestBody SignInDto signInDto){
        if(signInDto.getIme().isEmpty() || signInDto.getPrezime().isEmpty() || signInDto.getKorisnickoIme().isEmpty() || signInDto.getMejl().isEmpty()
                || signInDto.getLozinka().isEmpty() || signInDto.getPonovljenaLozinka().isEmpty())
            return new ResponseEntity("Uneti neispravni podaci", HttpStatus.BAD_REQUEST);

        if (!signInDto.getLozinka().equals(signInDto.getPonovljenaLozinka()))
            return new ResponseEntity("Lozinke nisu iste", HttpStatus.BAD_REQUEST);

        List<Korisnik> korisnici = korisnikService.findAll();

        for (Korisnik k : korisnici)
            if(signInDto.getMejl().equals(k.getMejl()))
                return new ResponseEntity("Mejl se vec koristi", HttpStatus.BAD_REQUEST);

        Korisnik korisnik = new Korisnik(signInDto.getIme(), signInDto.getPrezime(), signInDto.getKorisnickoIme(), signInDto.getMejl(), signInDto.getLozinka());
        this.korisnikService.save(korisnik);

        Polica p1 = new Polica("Want to read", true, korisnik);
        Polica p2 = new Polica("Currently reading", true, korisnik);
        Polica p3 = new Polica("Read", true, korisnik);
        policaService.save(p1);
        policaService.save(p2);
        policaService.save(p3);

        return ResponseEntity.ok(korisnik);
    }

    @PostMapping("/logout")
    public ResponseEntity Logout(HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        session.invalidate();
        return new ResponseEntity("Uspesno odjavljivanje", HttpStatus.OK);
    }

    @PutMapping("/korisnik/ime")
    public ResponseEntity izmeniIme(@RequestBody ImeDto imeDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        korisnik.setIme(imeDto.getIme());
        korisnikService.save(korisnik);
        return ResponseEntity.ok("Uspesna izmena imena");
    }

    @PutMapping("/korisnik/prezime")
    public ResponseEntity izmeniPrezime(@RequestBody PrezimeDto prezimeDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        korisnik.setPrezime(prezimeDto.getPrezime());
        korisnikService.save(korisnik);
        return ResponseEntity.ok("Uspesna izmena prezimena");
    }

    @PostMapping("/korisnik/datum")
    public ResponseEntity dodajDatum(@RequestBody DatumRodjenjaDto datumRodjenjaDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        korisnik.setDatumRodjenja(datumRodjenjaDto.getDatumRodjenja());
        korisnikService.save(korisnik);
        return ResponseEntity.ok("Uspesno unet datum rodjenja");
    }

    @PostMapping("/korisnik/profilna")
    public ResponseEntity dodajProfilnu(@RequestBody ProfilnaSlikaDto profilnaSlikaDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        korisnik.setProfilnaSlika(profilnaSlikaDto.getProfilnaSlika());
        korisnikService.save(korisnik);
        return ResponseEntity.ok("Uspesno uneta profilna slika");
    }

    @PostMapping("/korisnik/opis")
    public ResponseEntity dodajOpis(@RequestBody OpisDto opisDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        korisnik.setOpis(opisDto.getOpis());
        korisnikService.save(korisnik);
        return ResponseEntity.ok("Uspesno unet opis");
    }

    @PutMapping("/korisnik/lozinka")
    public ResponseEntity promeniLozinku(@RequestBody LozinkaDto lozinkaDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        if(!korisnik.getLozinka().equals(lozinkaDto.getStaraLozinka()))
            return new ResponseEntity("Lozinke se ne poklapaju", HttpStatus.BAD_REQUEST);

        korisnik.setLozinka(lozinkaDto.getNovaLozinka());
        korisnikService.save(korisnik);
        return ResponseEntity.ok("Uspesno promenjena lozinka");
    }

    @PutMapping("/korisnik/mejl")
    public ResponseEntity promeniMejl(@RequestBody MejlDto mejlDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        if(!korisnik.getMejl().equals(mejlDto.getStariMejl()))
            return new ResponseEntity("Mejlovi se ne poklapaju", HttpStatus.BAD_REQUEST);

        korisnik.setMejl(mejlDto.getNoviMejl());
        korisnikService.save(korisnik);
        return ResponseEntity.ok("Uspesno promenjen mejl");
    }
}
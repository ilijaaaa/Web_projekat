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
    private StavkaPoliceService stavkaPoliceService;
    @Autowired
    private  ZahtevZaAktivacijuService zahtevZaAktivacijuService;

    @GetMapping("/korisnici")
    public  List<Korisnik> getKorisinic() { return korisnikService.findAll(); }

    @GetMapping("/korisnik/{id}")
    public ResponseEntity<KorisnikDto> getKorisnik(@PathVariable(name = "id") Long id){
        Korisnik korisnik = korisnikService.findOne(id);

        if(korisnik == null){
            return new ResponseEntity("Korisnik ne postoji", HttpStatus.NOT_FOUND);
        }

        KorisnikRecenzijaDto korisnikRecenzijaDto = new KorisnikRecenzijaDto(korisnik.getIme(), korisnik.getPrezime(), korisnik.getKorisnickoIme(), korisnik.getProfilnaSlika());
        List<Polica> police = policaService.findAll(korisnik);
        Set<PolicaDto> policeDto = new HashSet<>();
        Set<KnjigaDto> knjigeDto = new HashSet<>();

        for(Polica p : police){
            List<StavkaPolice> stavkePolice = stavkaPoliceService.findAll(p);

            for(StavkaPolice sp : stavkePolice){
                KnjigaDto knjigaDto = new KnjigaDto();
                knjigaDto.setNaslov(sp.getKnjiga().getNaslov());
                knjigaDto.setSlika(sp.getKnjiga().getSlika());
                knjigaDto.setIsbn(sp.getKnjiga().getIsbn());
                knjigeDto.add(knjigaDto);
            }

            PolicaDto policaDto = new PolicaDto(p.getNaziv(), knjigeDto);
            policeDto.add(policaDto);
        }

        KorisnikDto korisnikDto = new KorisnikDto(korisnikRecenzijaDto, policeDto);

        return ResponseEntity.ok(korisnikDto);
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
    public ResponseEntity<KorisnikDto> login(@RequestBody LoginDto loginDto, HttpSession session){
        if(loginDto.getMejl().isEmpty() || loginDto.getLozinka().isEmpty())
            return new ResponseEntity("Uneti neispravni podaci", HttpStatus.BAD_REQUEST);

        Korisnik korisnik = korisnikService.login(loginDto.getMejl(), loginDto.getLozinka());

        if (korisnik == null)
            return new ResponseEntity("Korisnik ne postoji", HttpStatus.NOT_FOUND);

        session.setAttribute("korisnik", korisnik);

        KorisnikRecenzijaDto korisnikRecenzijaDto = new KorisnikRecenzijaDto(korisnik.getIme(), korisnik.getPrezime(), korisnik.getKorisnickoIme(), korisnik.getProfilnaSlika());
        List<Polica> police = policaService.findAll(korisnik);
        Set<PolicaDto> policeDto = new HashSet<>();
        Set<KnjigaDto> knjigeDto = new HashSet<>();

        for(Polica p : police){
            List<StavkaPolice> stavkePolice = stavkaPoliceService.findAll(p);

            for(StavkaPolice sp : stavkePolice){
                KnjigaDto knjigaDto = new KnjigaDto();
                knjigaDto.setNaslov(sp.getKnjiga().getNaslov());
                knjigaDto.setSlika(sp.getKnjiga().getSlika());
                knjigaDto.setIsbn(sp.getKnjiga().getIsbn());
                knjigeDto.add(knjigaDto);
            }

            PolicaDto policaDto = new PolicaDto(p.getNaziv(), knjigeDto);
            policeDto.add(policaDto);
        }

        KorisnikDto korisnikDto = new KorisnikDto(korisnikRecenzijaDto, policeDto);

        return ResponseEntity.ok(korisnikDto);
    }

    @PostMapping("/signin")
    public ResponseEntity<KorisnikDto> signin(@RequestBody SignInDto signInDto){
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

        KorisnikRecenzijaDto korisnikRecenzijaDto = new KorisnikRecenzijaDto(korisnik.getIme(), korisnik.getPrezime(), korisnik.getKorisnickoIme(), korisnik.getProfilnaSlika());
        List<Polica> police = policaService.findAll(korisnik);
        Set<PolicaDto> policeDto = new HashSet<>();
        Set<KnjigaDto> knjigeDto = new HashSet<>();

        for(Polica p : police){
            List<StavkaPolice> stavkePolice = stavkaPoliceService.findAll(p);

            for(StavkaPolice sp : stavkePolice){
                KnjigaDto knjigaDto = new KnjigaDto();
                knjigaDto.setNaslov(sp.getKnjiga().getNaslov());
                knjigaDto.setSlika(sp.getKnjiga().getSlika());
                knjigaDto.setIsbn(sp.getKnjiga().getIsbn());
                knjigeDto.add(knjigaDto);
            }

            PolicaDto policaDto = new PolicaDto(p.getNaziv(), knjigeDto);
            policeDto.add(policaDto);
        }

        KorisnikDto korisnikDto = new KorisnikDto(korisnikRecenzijaDto, policeDto);

        return ResponseEntity.ok(korisnikDto);
    }

    @PostMapping("/logout")
    public ResponseEntity Logout(HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        session.invalidate();
        return new ResponseEntity("Uspesno odjavljivanje", HttpStatus.OK);
    }

    @PutMapping("/korisnik")
    public ResponseEntity izmeniIme(@RequestBody AzuriranjeKorisnikaDto azuriranjeKorisnikaDto, HttpSession session){
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
            if(!korisnik.getMejl().equals(azuriranjeKorisnikaDto.getStariMejl()))
                return new ResponseEntity("Mejlovi se ne podudaraju", HttpStatus.BAD_REQUEST);

        korisnik.setMejl(azuriranjeKorisnikaDto.getMejl());

        korisnikService.save(korisnik);
        return ResponseEntity.ok("Uspesna izmena");
    }
}
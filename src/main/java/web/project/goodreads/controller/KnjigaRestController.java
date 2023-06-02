package web.project.goodreads.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.project.goodreads.dto.*;
import web.project.goodreads.entity.*;
import web.project.goodreads.service.AutorService;
import web.project.goodreads.service.KnjigaService;
import web.project.goodreads.service.RecenzijaService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class KnjigaRestController {
    @Autowired
    private KnjigaService knjigaService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private RecenzijaService recenzijaService;

    @GetMapping("/knjige")
    public ResponseEntity<List<Knjiga>> getKnjige(){
        List<Knjiga> knjige = knjigaService.findAll();

        return ResponseEntity.ok(knjige);
    }

    @GetMapping("/knjige/{id}")
    public ResponseEntity<Knjiga> getKnjiga(@PathVariable(name = "id") Long id){
        Knjiga knjiga = knjigaService.findOne(id);

        if(knjiga == null){
            return new ResponseEntity("Knjiga ne postoji", HttpStatus.NOT_FOUND);

        StringDto zanrDto = new StringDto(k.getZanr().getNaziv());
        KorisnikRecenzijaDto autor = new KorisnikRecenzijaDto(k.getAutor().getIme(), k.getAutor().getPrezime(), k.getAutor().getKorisnickoIme(), k.getAutor().getProfilnaSlika());
        KnjigaDto knjigaDto = new KnjigaDto(k.getNaslov(), k.getSlika(), k.getOpis(), k.getIsbn(), k.getDatum(), k.getBrStr(), zanrDto, autor);

        for(StavkaPolice sp : stavkePolice)
            if(sp.getRecenzija() != null){
                KorisnikRecenzijaDto korisnikRecenzijaDto = new KorisnikRecenzijaDto(sp.getRecenzija().getKorisnik().getIme(), sp.getRecenzija().getKorisnik().getPrezime(), sp.getRecenzija().getKorisnik().getKorisnickoIme(), sp.getRecenzija().getKorisnik().getProfilnaSlika());
                RecenzijaDto recenzijaDto = new RecenzijaDto(sp.getRecenzija().getOcena(), sp.getRecenzija().getTekst(), sp.getRecenzija().getDatum(), korisnikRecenzijaDto);
                recenzijeDto.add(recenzijaDto);
            }

        PregledKnjigeDto pregledKnjigeDto = new PregledKnjigeDto(knjigaDto, recenzijeDto);

        return ResponseEntity.ok(pregledKnjigeDto);
    }

    /*@PostMapping("/dodaj-knjigu")
    public ResponseEntity<String> dodajKnjigu(@RequestBody KnjigaDto knjigaDto) {
        List<Knjiga> knjige = knjigaService.findAll();

        for (Knjiga k : knjige)
            if (knjigaDto.getIsbn().equals(k.getIsbn()))
                return new ResponseEntity("Ova knjiga vec postoji", HttpStatus.BAD_REQUEST);

        List<Autor> autori = autorService.findAll();

        for (Autor a : autori)
        {
            if (knjigaDto.getAutor().getId().equals(a.getId()) && knjigaDto.getAutor().getIme().equals(a.getIme()) && knjigaDto.getAutor().getPrezime().equals(a.getPrezime()))
            {
                Knjiga knjiga = new Knjiga(knjigaDto.getNaslov(), knjigaDto.getSlika(), knjigaDto.getOpis(), knjigaDto.getIsbn(), knjigaDto.getDatum(), knjigaDto.getBrStr(), knjigaDto.getZanr(), knjigaDto.getAutor());
                this.knjigaService.save(knjiga);
                return new ResponseEntity("Uspesno dodata knjiga", HttpStatus.CREATED);
            }
        }

        return ResponseEntity.ok("Neuspesno dodavanje knjiga");
    }


    @PutMapping("/knjiga/naslov")
    public ResponseEntity<String> izmeniNaslov(@RequestBody NaslovDto naslovDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if ((korisnik == null) || (korisnik.getUloga() != Korisnik.Uloga.ADMINISTRATOR))
            return new ResponseEntity("Admin nije prijavljen", HttpStatus.FORBIDDEN);

        List<Knjiga> knjige = knjigaService.findAll();
        Knjiga izmenjena = null;

        for (Knjiga k : knjige)
            if(naslovDto.getIsbn().equals(k.getIsbn()))
                izmenjena = k;

        if(izmenjena == null)
            return new ResponseEntity("Knjiga ne postoji", HttpStatus.NOT_FOUND);

        izmenjena.setNaslov(naslovDto.getNaslov());
        knjigaService.save(izmenjena);
        return ResponseEntity.ok("Naslov knjige uspesno izmenjen");
    }

    @PutMapping("/knjiga/naslovna-fotografija")
    public ResponseEntity<String> izmeniNaslovnu(@RequestBody NaslovnaDto naslovnaDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if ((korisnik == null) || (korisnik.getUloga() != Korisnik.Uloga.ADMINISTRATOR))
            return new ResponseEntity("Admin nije prijavljen", HttpStatus.FORBIDDEN);

        List<Knjiga> knjige = knjigaService.findAll();
        Knjiga izmenjena = null;

        for (Knjiga k : knjige)
            if(naslovnaDto.getIsbn().equals(k.getIsbn()))
                izmenjena = k;

        if(izmenjena == null)
            return new ResponseEntity("Knjiga ne postoji", HttpStatus.NOT_FOUND);

        izmenjena.setSlika(naslovnaDto.getSlika());
        knjigaService.save(izmenjena);
        return ResponseEntity.ok("Naslovna fotografija knjige uspesno izmenjena");
    }

    @PutMapping("/knjiga/broj-strana")
    public ResponseEntity<String> izmeniBrojStrana(@RequestBody BrStrDto brStrDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if ((korisnik == null) || (korisnik.getUloga() != Korisnik.Uloga.ADMINISTRATOR))
            return new ResponseEntity("Admin nije prijavljen", HttpStatus.FORBIDDEN);

        List<Knjiga> knjige = knjigaService.findAll();
        Knjiga izmenjena = null;

        for (Knjiga k : knjige)
            if(brStrDto.getIsbn().equals(k.getIsbn()))
                izmenjena = k;

        if(izmenjena == null)
            return new ResponseEntity("Knjiga ne postoji", HttpStatus.NOT_FOUND);

        izmenjena.setBrStr(brStrDto.getBrStr());
        knjigaService.save(izmenjena);
        return ResponseEntity.ok("Broj strana knjige uspesno izmenjen");
    }

    @PutMapping("/knjiga/opis-knjige")
    public ResponseEntity<String> izmeniOpisK(@RequestBody OpisKDto opisDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if ((korisnik == null) || (korisnik.getUloga() != Korisnik.Uloga.ADMINISTRATOR))
            return new ResponseEntity("Admin nije prijavljen", HttpStatus.FORBIDDEN);

        List<Knjiga> knjige = knjigaService.findAll();
        Knjiga izmenjena = null;

        for (Knjiga k : knjige)
            if(opisDto.getIsbn().equals(k.getIsbn()))
                izmenjena = k;

        if(izmenjena == null)
            return new ResponseEntity("Knjiga ne postoji", HttpStatus.NOT_FOUND);

        izmenjena.setOpis(opisDto.getOpis());
        knjigaService.save(izmenjena);
        return ResponseEntity.ok("Opis knjige uspesno izmenjen");
    }


    @Transactional
    @DeleteMapping("/knjiga/delete")
    public ResponseEntity<List<Knjiga>> deleteKnjiga(@RequestBody KnjigaDto knjigaDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if ((korisnik == null) || (korisnik.getUloga() != Korisnik.Uloga.ADMINISTRATOR))
            return new ResponseEntity("Admin nije prijavljen", HttpStatus.FORBIDDEN);

        List<Knjiga> knjige = knjigaService.findAll();
        Knjiga knjiga = null;

        for (Knjiga k : knjige)
            if(knjigaDto.getIsbn().equals(k.getIsbn()))
                knjiga = k;

        if(knjiga == null)
            return new ResponseEntity("Knjiga ne postoji", HttpStatus.NOT_FOUND);

        Set<Recenzija> recenzije = recenzijaService.findOne(knjigaDto.getId());
        if(!recenzije.isEmpty())
            return new ResponseEntity("Brisanje knjige nije moguce", HttpStatus.FORBIDDEN);

        knjigaService.deleteOne(knjiga);
        return  ResponseEntity.ok(knjigaService.findAll());
    }*/


}
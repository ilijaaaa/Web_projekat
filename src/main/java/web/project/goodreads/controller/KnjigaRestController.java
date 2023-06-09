package web.project.goodreads.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.project.goodreads.dto.*;
import web.project.goodreads.entity.*;
import web.project.goodreads.service.*;

import java.util.HashSet;
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
    private StavkaPoliceService stavkaPoliceService;

    @Autowired
    private RecenzijaService recenzijaService;

    @Autowired
    private ZanrService zanrService;

    @GetMapping("/knjige")
    public ResponseEntity<Set<KnjigaDto>> getKnjige(){
        List<Knjiga> knjige = knjigaService.findAll();
        Set<KnjigaDto> knjigeDto = new HashSet<>();

        for(Knjiga k : knjige){
            StringDto zanr = new StringDto(k.getZanr().getNaziv());
            KorisnikRecenzijaDto autor = new KorisnikRecenzijaDto(k.getAutor().getIme(), k.getAutor().getPrezime(), k.getAutor().getKorisnickoIme(), k.getAutor().getProfilnaSlika());
            KnjigaDto knjigaDto = new KnjigaDto(k.getNaslov(), k.getSlika(), k.getOpis(), k.getIsbn(), k.getDatum(), k.getBrStr(), zanr, autor);
            knjigeDto.add(knjigaDto);
        }

        return ResponseEntity.ok(knjigeDto);
    }

    @GetMapping("/knjige/{naslov}")
    public ResponseEntity<Set<KnjigaDto>> getKnjiga(@PathVariable(name = "naslov") String naslov){
        Set<Knjiga> knjige = knjigaService.findAll(naslov);

        if(knjige.isEmpty()){
            return new ResponseEntity("Knjige sa takvim naslovom ne postoje", HttpStatus.NOT_FOUND);
        }

        Set<KnjigaDto> knjigeDto = new HashSet<>();

        for(Knjiga k : knjige){
            StringDto zanr = new StringDto(k.getZanr().getNaziv());
            KorisnikRecenzijaDto autor = new KorisnikRecenzijaDto(k.getAutor().getIme(), k.getAutor().getPrezime(), k.getAutor().getKorisnickoIme(), k.getAutor().getProfilnaSlika());
            KnjigaDto knjigaDto = new KnjigaDto(k.getNaslov(), k.getSlika(), k.getOpis(), k.getIsbn(), k.getDatum(), k.getBrStr(), zanr, autor);
            knjigeDto.add(knjigaDto);
        }

        return ResponseEntity.ok(knjigeDto);
    }

    @GetMapping("/knjiga/{id}")
    public ResponseEntity<PregledKnjigeDto> getKnjiga(@PathVariable(name = "id") Long id){
        Knjiga k = knjigaService.findOne(id);
        Set<RecenzijaDto> recenzijeDto = new HashSet<>();
        List<StavkaPolice> stavkePolice = stavkaPoliceService.findAll(k);

        if(k == null)
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
    public ResponseEntity<String> dodajKnjigu(@RequestBody KnjigaDto knjigaDto, HttpSession session) {
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if ((korisnik == null) || (korisnik.getUloga() != Korisnik.Uloga.CITALAC))
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        List<Knjiga> knjige = knjigaService.findAll();

        for (Knjiga k : knjige)
            if (knjigaDto.getIsbn().equals(k.getIsbn()))
                return new ResponseEntity("Ova knjiga vec postoji", HttpStatus.BAD_REQUEST);

        Autor autor = autorService.findOne(knjigaDto.getAutor().getId());
        if(autor == null)
            return new ResponseEntity("Ne postoji taj autor u bazi", HttpStatus.BAD_REQUEST);

        //zbrka oko zanra
        (!zanr.isEmpty()) {
            Zanr zanrzanr = zanr.iterator().next();
            Knjiga knjiga = new Knjiga(knjigaDto.getNaslov(), knjigaDto.getSlika(), knjigaDto.getOpis(), knjigaDto.getIsbn(), knjigaDto.getDatum(), knjigaDto.getBrStr(), zanrzanr, autor);
            knjigaService.save(knjiga);
        } else {
            return new ResponseEntity("Ne postoji taj zanr u bazi", HttpStatus.BAD_REQUEST);
        }
        if(zanr == null)
            return new ResponseEntity("Ne postoji taj zanr u bazi", HttpStatus.BAD_REQUEST);

        List<Zanr> zanrovi = zanrService.findAll();
        Zanr zanr = null;
        for (Zanr z : zanrovi)
            if (knjigaDto.getZanr().getValue().equals(z.getNaziv())) {
                zanr = z;
            }

        Knjiga knjiga = new Knjiga(knjigaDto.getNaslov(), knjigaDto.getSlika(), knjigaDto.getOpis(), knjigaDto.getIsbn(), knjigaDto.getDatum(), knjigaDto.getBrStr(), autor);
        knjigaService.save(knjiga);

        return ResponseEntity.ok("Kao Knjiga uspesno dodata");
    }*/

    @PutMapping("/knjiga/izmeni")
    public ResponseEntity<String> azurirajKnjigu(@RequestBody AzuriranjeKnjigeDto azuriranjeKnjigeDto, HttpSession session)
    {
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if ((korisnik == null) || (korisnik.getUloga() == Korisnik.Uloga.CITALAC))
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        List<Knjiga> knjige = knjigaService.findAll();
        Knjiga knjiga = null;

        for (Knjiga k : knjige)
            if (azuriranjeKnjigeDto.getIsbn().equals(k.getIsbn()))
                knjiga = k;

        if(knjiga == null)
            return new ResponseEntity("Ova knjiga ne postoji", HttpStatus.NOT_FOUND);

        if(azuriranjeKnjigeDto.getNaslov() != null)
            knjiga.setNaslov(azuriranjeKnjigeDto.getNaslov());

        if(azuriranjeKnjigeDto.getSlika() != null)
            knjiga.setSlika(azuriranjeKnjigeDto.getSlika());

        if(azuriranjeKnjigeDto.getOpis() != null)
            knjiga.setOpis(azuriranjeKnjigeDto.getOpis());

        if(azuriranjeKnjigeDto.getDatum() != null)
            knjiga.setDatum(azuriranjeKnjigeDto.getDatum());

        if(azuriranjeKnjigeDto.getOpis() != null)
            knjiga.setOpis(azuriranjeKnjigeDto.getOpis());

        if(azuriranjeKnjigeDto.getBrStr() != 0)
            knjiga.setBrStr(azuriranjeKnjigeDto.getBrStr());
/*
        if(azuriranjeKnjigeDto.getZanr() != null)
            knjiga.setZanr(azuriranjeKnjigeDto.getZanr());

        if(azuriranjeKnjigeDto.getAutor() != null)
            knjiga.setAutor(azuriranjeKnjigeDto.getAutor());
*/
        knjigaService.save(knjiga);
        return ResponseEntity.ok("Uspesna izmena");
    }

    /*@Transactional
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

        //Set<Recenzija> recenzije = recenzijaService.findOneSet(knjigaDto.getId());
        //if(!recenzije.isEmpty())
        //    return new ResponseEntity("Brisanje knjige nije moguce", HttpStatus.FORBIDDEN);

        Recenzija r = recenzijaService.findOne(knjigaDto.getId());
        if(r != null)
            return new ResponseEntity("Brisanje knjige nije moguce", HttpStatus.FORBIDDEN);

        knjigaService.deleteOne(knjiga);
        return  ResponseEntity.ok(knjigaService.findAll());
    }*/


}
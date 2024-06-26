package web.project.goodreads.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.project.goodreads.dto.*;
import web.project.goodreads.entity.*;
import web.project.goodreads.service.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin
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
    private ZanrService zanrService;
    @Autowired
    private KorisnikService korisnikService;

    @GetMapping("/knjige")
    public ResponseEntity<List<Knjiga>> pretragaKnjige() { return ResponseEntity.ok(knjigaService.findAll()); }

    @GetMapping("/knjige/{naslov}")
    public ResponseEntity<Set<Knjiga>> pretragaKnjige(@PathVariable(name = "naslov") String naslov){
        Set<Knjiga> knjige = knjigaService.findAll(naslov);

        if(knjige.isEmpty()){
            return new ResponseEntity("Knjige sa takvim naslovom ne postoje", HttpStatus.NOT_FOUND);
        }

        Set<Knjiga> knjigas = new HashSet<>();

        for(Knjiga k : knjige){
            Autor autor = new Autor();
            autor.setId(k.getAutor().getId());
            autor.setIme(k.getAutor().getIme());
            autor.setPrezime(k.getAutor().getPrezime());

            Knjiga knjiga = new Knjiga();
            knjiga.setId(k.getId());
            knjiga.setNaslov(k.getNaslov());
            knjiga.setAutor(autor);
            knjiga.setSlika(k.getSlika());
            knjiga.setOcena(k.getOcena());
            knjiga.setDatum(k.getDatum());

            knjigas.add(knjiga);
        }

        return ResponseEntity.ok(knjigas);
    }

    @GetMapping("/knjiga/{id}/{sessionId}")
    public ResponseEntity<PregledKnjigeDto> pregledKnjige(@PathVariable(name = "id") Long id, @PathVariable(name = "sessionId") String sessionId){
        Knjiga k = knjigaService.findOne(id);

        if(k == null)
            return new ResponseEntity("Knjiga ne postoji", HttpStatus.NOT_FOUND);

        Autor autor = new Autor();
        autor.setId(k.getAutor().getId());
        autor.setIme(k.getAutor().getIme());
        autor.setPrezime(k.getAutor().getPrezime());
        autor.setProfilnaSlika(k.getAutor().getProfilnaSlika());
        autor.setOpis(k.getAutor().getOpis());
        autor.setAktivan(k.getAutor().getAktivan());

        Knjiga knjiga = new Knjiga(k.getNaslov(), k.getSlika(), k.getOpis(), k.getIsbn(), k.getDatum(), k.getBrStr(), k.getZanr(), autor);
        knjiga.setId(k.getId());
        knjiga.setOcena(k.getOcena());

        Set<Recenzija> recenzije = new HashSet<>();

        for(StavkaPolice sp : stavkaPoliceService.findAll(k)){
            if(sp.getRecenzija() != null){
                Korisnik korisnik = new Korisnik();
                korisnik.setId(sp.getRecenzija().getKorisnik().getId());
                korisnik.setSessionId(sp.getRecenzija().getKorisnik().getSessionId());
                korisnik.setProfilnaSlika(sp.getRecenzija().getKorisnik().getProfilnaSlika());
                korisnik.setKorisnickoIme(sp.getRecenzija().getKorisnik().getKorisnickoIme());

                Recenzija recenzija = new Recenzija();
                recenzija.setId(sp.getRecenzija().getId());
                recenzija.setOcena(sp.getRecenzija().getOcena());
                recenzija.setTekst(sp.getRecenzija().getTekst());
                recenzija.setDatum(sp.getRecenzija().getDatum());
                recenzija.setKorisnik(korisnik);

                recenzije.add(recenzija);
            }
        }

        Korisnik korisnik = korisnikService.findBySessionId(sessionId);

        if(korisnik == null)
            return ResponseEntity.ok(new PregledKnjigeDto(knjiga, recenzije, null, null));
        else
            return ResponseEntity.ok(new PregledKnjigeDto(knjiga, recenzije, korisnik.getUloga().toString(), korisnik.getId()));
    }

    @GetMapping("/knjige/zanr/{naziv}")
    public ResponseEntity<Set<Knjiga>> pretragaKnjigeZanr(@PathVariable(name = "naziv") String naziv){
        Zanr zanr = zanrService.findOne(naziv);
        if (zanr == null) {
            return new ResponseEntity("Žanr sa datim nazivom ne postoji", HttpStatus.NOT_FOUND);
        }

        Set<Knjiga> knjige = knjigaService.findAllByZanr(naziv);

        if(knjige.isEmpty()){
            return new ResponseEntity("Knjige tog žanra ne postoje", HttpStatus.NOT_FOUND);
        }

        Set<Knjiga> knjigas = new HashSet<>();

        for(Knjiga k : knjige){
            Autor autor = new Autor();
            autor.setId(k.getAutor().getId());
            autor.setIme(k.getAutor().getIme());
            autor.setPrezime(k.getAutor().getPrezime());

            Knjiga knjiga = new Knjiga();
            knjiga.setId(k.getId());
            knjiga.setNaslov(k.getNaslov());
            knjiga.setAutor(autor);
            knjiga.setSlika(k.getSlika());
            knjiga.setOcena(k.getOcena());
            knjiga.setDatum(k.getDatum());

            knjigas.add(knjiga);
        }

        return ResponseEntity.ok(knjigas);
    }

    @PostMapping("/knjiga")
    public ResponseEntity<Knjiga> dodajKnjigu(@RequestBody KnjigaDto knjigaDto, String sessionId) {
        Korisnik korisnik = korisnikService.findBySessionId(sessionId);

        if ((korisnik == null) || (korisnik.getUloga() == Korisnik.Uloga.CITALAC))
            return new ResponseEntity("Nemate prava", HttpStatus.FORBIDDEN);

        for (Knjiga k : knjigaService.findAll())
            if (knjigaDto.getIsbn().equals(k.getIsbn()))
                return new ResponseEntity("Ova knjiga već postoji", HttpStatus.BAD_REQUEST);

        Autor autor = autorService.findOne(knjigaDto.getAutor());
        if(autor == null)
            return new ResponseEntity("Ne postoji taj autor u bazi", HttpStatus.BAD_REQUEST);

        Zanr zanr = zanrService.findOne(knjigaDto.getZanr());

        if(zanr == null)
            return new ResponseEntity("Ne postoji taj žanr u bazi", HttpStatus.BAD_REQUEST);

        if(korisnik.getUloga() == Korisnik.Uloga.AUTOR)
            autor = (Autor)korisnik;

        Knjiga knjiga = new Knjiga(knjigaDto.getNaslov(), knjigaDto.getSlika(), knjigaDto.getOpis(), knjigaDto.getIsbn(), knjigaDto.getDatum(), knjigaDto.getBrStr(), zanr, autor);
        knjiga.setOcena(new BigDecimal(0));
        knjigaService.save(knjiga);

        return ResponseEntity.ok(knjiga);
    }

    @PutMapping("/knjiga/{id}")
    public ResponseEntity<Knjiga> azurirajKnjigu(@PathVariable(name="id") Long id, @RequestBody KnjigaDto knjigaDto, String sessionId)
    {
        Korisnik korisnik = korisnikService.findBySessionId(sessionId);

        if ((korisnik == null) || (korisnik.getUloga() == Korisnik.Uloga.CITALAC))
            return new ResponseEntity("Nemate prava", HttpStatus.FORBIDDEN);

        Knjiga knjiga = knjigaService.findOne(id);

        if(!knjiga.getAutor().getId().equals(korisnik.getId()) && korisnik.getUloga().equals(Korisnik.Uloga.AUTOR))
            return new ResponseEntity("Nije vaša knjiga za izmenu", HttpStatus.FORBIDDEN);

        if(knjiga == null)
            return new ResponseEntity("Ova knjiga ne postoji", HttpStatus.NOT_FOUND);

        if(!knjigaDto.getNaslov().isEmpty())
            knjiga.setNaslov(knjigaDto.getNaslov());

        if(!knjigaDto.getSlika().isEmpty())
            knjiga.setSlika(knjigaDto.getSlika());

        if(!knjigaDto.getOpis().isEmpty())
            knjiga.setOpis(knjigaDto.getOpis());

        if(knjigaDto.getDatum() != null)
            knjiga.setDatum(knjigaDto.getDatum());

        if(knjigaDto.getBrStr() != 0)
            knjiga.setBrStr(knjigaDto.getBrStr());

        Zanr zanr = zanrService.findOne(knjigaDto.getZanr());

        if(zanr == null)
            return new ResponseEntity("Ne postoji taj žanr u bazi", HttpStatus.BAD_REQUEST);

        knjiga.setZanr(zanr);

        Autor autor = autorService.findOne(knjigaDto.getAutor());
        if(autor == null)
            return new ResponseEntity("Ne postoji taj autor u bazi", HttpStatus.BAD_REQUEST);

        if(korisnik.getUloga() == Korisnik.Uloga.AUTOR)
            autor = (Autor)korisnik;

        knjiga.setAutor(autor);

        knjigaService.save(knjiga);
        return ResponseEntity.ok(knjiga);
    }

    @Transactional
    @DeleteMapping("/knjiga/{id}/{sessionId}")
    public ResponseEntity<List<Knjiga>> obrisiKnjigu(@PathVariable(name="id") Long id, @PathVariable(name="sessionId") String sessionId){
        Korisnik korisnik = korisnikService.findBySessionId(sessionId);

        if ((korisnik == null) || (!korisnik.getUloga().equals(Korisnik.Uloga.ADMINISTRATOR)))
            return new ResponseEntity("Admin nije prijavljen", HttpStatus.FORBIDDEN);

        Knjiga knjiga = knjigaService.findOne(id);

        if(knjiga == null)
            return new ResponseEntity("Knjiga ne postoji", HttpStatus.NOT_FOUND);

        for(StavkaPolice sp : stavkaPoliceService.findAll(knjiga))
            if(sp.getRecenzija() != null)
                return new ResponseEntity("Brisanje knjige nije moguće", HttpStatus.FORBIDDEN);

        knjigaService.delete(id);
        return  ResponseEntity.ok(knjigaService.findAll());
    }
}
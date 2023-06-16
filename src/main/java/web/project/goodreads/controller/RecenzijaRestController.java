package web.project.goodreads.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.project.goodreads.dto.RecenzijaDto;
import web.project.goodreads.entity.*;
import web.project.goodreads.service.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RecenzijaRestController {
    @Autowired
    private RecenzijaService recenzijaService;
    @Autowired
    private KnjigaService knjigaService;
    @Autowired
    private StavkaPoliceService stavkaPoliceService;
    @Autowired
    private PolicaService policaService;

    @PostMapping("/recenzija/{knjiga_id}")
    public ResponseEntity<Recenzija> recenzija(@RequestBody RecenzijaDto recenzijaDto, @PathVariable(name = "knjiga_id") Long knjiga_id, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if(korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        if(knjigaService.findOne(knjiga_id) == null)
            return new ResponseEntity("Knjiga ne postoji", HttpStatus.BAD_REQUEST);

        StavkaPolice stavka = null;

        for(Polica p : policaService.findAll(korisnik))
            if(p.getNaziv().equals("Read"))
                for(StavkaPolice sp : stavkaPoliceService.findAll(p))
                    if(sp.getKnjiga().getId().equals(knjiga_id))
                        stavka = sp;

        if(stavka == null)
            return new ResponseEntity("Knjiga ne postoji na trazenoj polici", HttpStatus.NOT_FOUND);

        if(stavka.getRecenzija() != null)
            return new ResponseEntity("Knjiga vec ima recenziju", HttpStatus.FORBIDDEN);

        Recenzija recenzija = new Recenzija(recenzijaDto.getOcena(), recenzijaDto.getTekst(), recenzijaDto.getDatum(), korisnik, stavka);
        recenzijaService.save(recenzija);

        return ResponseEntity.ok(recenzija(recenzija));
    }

    @PutMapping("/recenzija/{recenzija_id}")
    public ResponseEntity<Recenzija> azuriranjeRecenzije(@RequestBody RecenzijaDto recenzijaDto, @PathVariable(name = "recenzija_id") Long recenzija_id, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if(korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        Recenzija recenzija = recenzijaService.findOne(recenzija_id);

        if(recenzija == null)
            return new ResponseEntity("Recenzija ne postoji", HttpStatus.NOT_FOUND);

        if(recenzijaDto.getOcena() != 0)
            recenzija.setOcena(recenzijaDto.getOcena());

        if(!recenzijaDto.getTekst().isEmpty())
            recenzija.setTekst(recenzijaDto.getTekst());

        if(recenzijaDto.getDatum() != null)
            recenzija.setDatum(recenzijaDto.getDatum());

        recenzijaService.save(recenzija);

        return ResponseEntity.ok(recenzija(recenzija));
    }

    private Recenzija recenzija(Recenzija r){
        Recenzija t = new Recenzija();
        t.setId(r.getId());
        t.setOcena(r.getOcena());
        t.setTekst(r.getTekst());
        t.setDatum(r.getDatum());

        return t;
    }
}

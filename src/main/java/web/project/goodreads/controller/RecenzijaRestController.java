package web.project.goodreads.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.project.goodreads.dto.RecenzijaDto;
import web.project.goodreads.entity.Korisnik;
import web.project.goodreads.entity.Polica;
import web.project.goodreads.entity.Recenzija;
import web.project.goodreads.entity.StavkaPolice;
import web.project.goodreads.service.PolicaService;
import web.project.goodreads.service.RecenzijaService;
import web.project.goodreads.service.StavkaPoliceService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class RecenzijaRestController {
    @Autowired
    private RecenzijaService recenzijaService;
    @Autowired
    private StavkaPoliceService stavkaPoliceService;
    @Autowired
    private PolicaService policaService;

    @GetMapping("/recenzije")
    public ResponseEntity<List<Recenzija>> getRecenzije() { return ResponseEntity.ok(recenzijaService.findAll()); }

    //@GetMapping("/recenzije/{id}")
    //public ResponseEntity<Set<Recenzija>> getRecenzijeKnjige(@PathVariable(name = "id") Long id) { return ResponseEntity.ok(recenzijaService.findOne(id)); }

    @PostMapping("/recenzija/{knjiga_id}")
    public ResponseEntity<RecenzijaDto> addRecenzija(@RequestBody RecenzijaDto recenzijaDto, @PathVariable(name = "knjiga_id") Long knjiga_id, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if(korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        StavkaPolice stavka = null;

        for(Polica p : policaService.findAll(korisnik))
            if(p.getNaziv().equals("Read"))
                for(StavkaPolice sp : stavkaPoliceService.findAll(p))
                    if(sp.getKnjiga().getId().equals(knjiga_id))
                        stavka = sp;

        if(stavka == null)
            return new ResponseEntity("Knjiga ne postoji na trazenoj polici", HttpStatus.NOT_FOUND);

        Recenzija recenzija = new Recenzija(recenzijaDto.getOcena(), recenzijaDto.getTekst(), recenzijaDto.getDatum(), korisnik, stavka);
        recenzijaService.save(recenzija);

        return ResponseEntity.ok(recenzijaDto);
    }

    @PutMapping("/recenzija/{recenzija_id}")
    public ResponseEntity<RecenzijaDto> azuriranjeRecenzije(@RequestBody RecenzijaDto recenzijaDto, @PathVariable(name = "recenzija_id") Long recenzija_id, HttpSession session){

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

        RecenzijaDto r = new RecenzijaDto();
        r.setDatum(recenzija.getDatum());
        r.setOcena(recenzija.getOcena());
        r.setTekst(recenzija.getTekst());
        return ResponseEntity.ok(r);
    }
}

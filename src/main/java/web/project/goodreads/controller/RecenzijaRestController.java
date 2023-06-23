package web.project.goodreads.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.project.goodreads.dto.RecenzijaDto;
import web.project.goodreads.entity.*;
import web.project.goodreads.service.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    @Autowired
    private KorisnikService korisnikService;

    @PostMapping("/recenzija/{knjiga_id}")
    public ResponseEntity<String> recenzija(@RequestBody RecenzijaDto recenzijaDto, @PathVariable(name = "knjiga_id") Long knjiga_id, String sessionId){
        Korisnik korisnik = korisnikService.findBySessionId(sessionId);

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
            return new ResponseEntity("Knjiga ne postoji na traženoj polici", HttpStatus.NOT_FOUND);

        if(stavka.getRecenzija() != null)
            return new ResponseEntity("Knjiga već ima recenziju", HttpStatus.FORBIDDEN);

        float suma = 0;
        int br = 0;

        for(StavkaPolice sp : stavkaPoliceService.findAll(knjigaService.findOne(knjiga_id)))
            if(sp.getRecenzija() != null){
                suma += sp.getRecenzija().getOcena();
                br++;
            }

        knjigaService.findOne(knjiga_id).setOcena(new BigDecimal(suma/br));

        Recenzija recenzija = new Recenzija(recenzijaDto.getOcena(), recenzijaDto.getTekst(), LocalDate.parse(recenzijaDto.getDatum(), DateTimeFormatter.ISO_DATE), korisnik, stavka);
        recenzijaService.save(recenzija);

        return ResponseEntity.ok("Recenzija uspešno dodata");
    }

    @PutMapping("/recenzija/{recenzija_id}/knjiga/{knjiga_id}")
    public ResponseEntity<String> azuriranjeRecenzije(@RequestBody RecenzijaDto recenzijaDto, @PathVariable(name = "recenzija_id") Long recenzija_id, @PathVariable(name = "knjiga_id") Long knjiga_id, String sessionId){
        Korisnik korisnik = korisnikService.findBySessionId(sessionId);

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
            recenzija.setDatum(LocalDate.parse(recenzijaDto.getDatum(), DateTimeFormatter.ISO_DATE));

        float suma = 0;
        int br = 0;

        for(StavkaPolice sp : stavkaPoliceService.findAll(knjigaService.findOne(knjiga_id)))
            if(sp.getRecenzija() != null){
                suma += sp.getRecenzija().getOcena();
                br++;
            }

        knjigaService.findOne(knjiga_id).setOcena(new BigDecimal(suma/br));

        recenzijaService.save(recenzija);

        return ResponseEntity.ok("Recenzija uspešno izmenjena");
    }

    @GetMapping("/recenzija/{id}")
    public ResponseEntity<Recenzija> getRecenzija(@PathVariable(name = "id") Long id){
        Recenzija r = recenzijaService.findOne(id);

        if(r == null)
            return new ResponseEntity("Recenzija ne postoji", HttpStatus.NOT_FOUND);

        Recenzija recenzija = new Recenzija();
        recenzija.setOcena(r.getOcena());
        recenzija.setTekst(r.getTekst());

        return ResponseEntity.ok(recenzija);
    }
}

package web.project.goodreads.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import web.project.goodreads.dto.*;
import web.project.goodreads.entity.*;
import web.project.goodreads.service.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PolicaRestController {
    @Autowired
    private PolicaService policaService;
    @Autowired
    private StavkaPoliceService stavkaPoliceService;
    @Autowired
    private KnjigaService knjigaService;
    @Autowired
    private RecenzijaService recenzijaService;

    @PostMapping("/polica")
    public ResponseEntity<Set<Polica>> dodajPolicu(@RequestBody StringDto stringDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if(korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        List<Polica> police = policaService.findAll(korisnik);

        for(Polica p : police)
            if(stringDto.getValue().equals(p.getNaziv()))
                return new ResponseEntity("Polica vec postoji", HttpStatus.BAD_REQUEST);

        Polica polica = new Polica(stringDto.getValue(), false, korisnik);
        policaService.save(polica);

        return ResponseEntity.ok(police(korisnik));
    }

    @Transactional
    @DeleteMapping("/polica/{id}")
    public ResponseEntity<Set<Polica>> izbrisiPolicu(@PathVariable(name = "id") Long id, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if(korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        if(policaService.findOne(id) == null)
            return new ResponseEntity("Polica ne postoji", HttpStatus.NOT_FOUND);

        if(policaService.findOne(id).isPrimarno())
            return new ResponseEntity("Zabranjeno brisanje primarnih polica", HttpStatus.FORBIDDEN);

        policaService.deleteOne(id);

        return ResponseEntity.ok(police(korisnik));
    }

    private Set<Polica> police(Korisnik korisnik){
        Set<Polica> policas = new HashSet<>();

        for(Polica p : policaService.findAll(korisnik)){
            Polica t = new Polica();
            t.setId(p.getId());
            t.setNaziv(p.getNaziv());
            policas.add(t);
        }

        return policas;
    }

    @PostMapping("/polica/{polica_id}/knjiga/{knjiga_id}")
    public ResponseEntity<Set<PolicaDto>> dodajKnjiguNaPolicu(@PathVariable(name = "polica_id") Long polica_id, @PathVariable(name = "knjiga_id") Long knjiga_id, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if(korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        Polica p = policaService.findOne(polica_id);
        Knjiga k = knjigaService.findOne(knjiga_id);
        boolean flag = false;

        if(p == null)
            return new ResponseEntity("Polica ne postoji", HttpStatus.NOT_FOUND);
        if(k == null)
            return new ResponseEntity("Knjiga ne postoji", HttpStatus.NOT_FOUND);

        if(p.isPrimarno()) {
            for (Polica polica : policaService.findAll(korisnik))
                if (polica.isPrimarno())
                    for (StavkaPolice sp : stavkaPoliceService.findAll(polica))
                        if (sp.getKnjiga().getId().equals(knjiga_id))
                            return new ResponseEntity("Knjiga vec postoji na primarnoj polici", HttpStatus.FORBIDDEN);
        }
        else {
            for (Polica polica : policaService.findAll(korisnik))
                if(polica.isPrimarno())
                    for (StavkaPolice sp : stavkaPoliceService.findAll(polica))
                        if (sp.getKnjiga().getId().equals(knjiga_id))
                            flag = true;
        }

        if(!p.isPrimarno() && !flag)
            return new ResponseEntity("Knjiga ne postoji na primarnoj polici", HttpStatus.FORBIDDEN);

        for (StavkaPolice sp : stavkaPoliceService.findAll(p))
            if (sp.getKnjiga().getId().equals(knjiga_id))
                return new ResponseEntity("Knjiga vec postoji na polici", HttpStatus.FORBIDDEN);

        StavkaPolice stavka = new StavkaPolice(p, k);
        stavkaPoliceService.save(stavka);

        return ResponseEntity.ok(policeDto(k, korisnik));
    }

    @DeleteMapping("/polica/{polica_id}/knjiga/{knjiga_id}")
    public ResponseEntity<Set<PolicaDto>> izbrisiKnjigu(@PathVariable(name = "polica_id") Long polica_id, @PathVariable(name = "knjiga_id") Long knjiga_id, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if(korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        Polica p = policaService.findOne(polica_id);
        Knjiga k = knjigaService.findOne(knjiga_id);

        if(p == null)
            return new ResponseEntity("Polica ne postoji", HttpStatus.NOT_FOUND);
        if(k == null)
            return new ResponseEntity("Knjiga ne postoji", HttpStatus.NOT_FOUND);

        StavkaPolice stavka = null;

        for(StavkaPolice sp : stavkaPoliceService.findAll(p))
            if(sp.getKnjiga().getId().equals(knjiga_id))
                stavka = sp;

        if(stavka == null)
            return new ResponseEntity("Knjiga ne postoji na trazenoj polici", HttpStatus.NOT_FOUND);

        if(p.getNaziv().equals("Read"))
            if(stavka.getRecenzija() != null)
                recenzijaService.delete(stavka.getRecenzija().getId());

        stavkaPoliceService.delete(stavka.getId());

        if(p.isPrimarno())
            for(Polica polica : policaService.findAll(korisnik))
                for(StavkaPolice sp : stavkaPoliceService.findAll(polica))
                    if (sp.getKnjiga().getId().equals(knjiga_id))
                        stavkaPoliceService.delete(sp.getId());

        return ResponseEntity.ok(policeDto(k, korisnik));
    }

    private Set<PolicaDto> policeDto(Knjiga k, Korisnik korisnik){
        Set<PolicaDto> policeDto = new HashSet<>();

        for(Polica polica : policaService.findAll(korisnik)){
            Set<Knjiga> knjige = new HashSet<>();

            for(StavkaPolice sp : stavkaPoliceService.findAll(polica)){
                Autor autor = new Autor();
                autor.setId(k.getAutor().getId());
                autor.setIme(k.getAutor().getIme());
                autor.setPrezime(k.getAutor().getPrezime());

                Knjiga knjiga = new Knjiga();
                knjiga.setId(sp.getKnjiga().getId());
                knjiga.setSlika(sp.getKnjiga().getSlika());
                knjiga.setNaslov(sp.getKnjiga().getNaslov());
                knjiga.setAutor(autor);
                knjiga.setOcena(sp.getKnjiga().getOcena());
                knjige.add(knjiga);
            }

            PolicaDto policaDto = new PolicaDto(polica.getId(), polica.getNaziv(), knjige);
            policeDto.add(policaDto);
        }

        return policeDto;
    }
}
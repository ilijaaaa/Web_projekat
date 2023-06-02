package web.project.goodreads.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import web.project.goodreads.dto.*;
import web.project.goodreads.entity.Knjiga;
import web.project.goodreads.entity.Korisnik;
import web.project.goodreads.entity.Polica;
import web.project.goodreads.entity.StavkaPolice;
import web.project.goodreads.service.KnjigaService;
import web.project.goodreads.service.PolicaService;
import web.project.goodreads.service.StavkaPoliceService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class PolicaRestController {
    @Autowired
    private PolicaService policaService;
    @Autowired
    private StavkaPoliceService stavkaPoliceService;
    @Autowired
    private KnjigaService knjigaService;

    @GetMapping("/police")
    public ResponseEntity<List<Polica>> getPolice() { return ResponseEntity.ok(policaService.findAll()); }

    /*@GetMapping("/police/{naziv}")
    public ResponseEntity<List<Polica>> getPoliceKorisnika(@PathVariable(name = "naziv") String naziv) { return ResponseEntity.ok(policaService.findOne(naziv)); }*/

    @PostMapping("/polica")
    public ResponseEntity<Set<PolicaDto>> addPolica(@RequestBody StringDto stringDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if(korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        for(Polica p : policaService.findAll(korisnik))
            if(stringDto.getValue().equals(p.getNaziv()))
                return new ResponseEntity("Polica vec postoji", HttpStatus.BAD_REQUEST);

        Polica polica = new Polica(stringDto.getValue(), false, korisnik);
        policaService.save(polica);

        Set<PolicaDto> policeDto = new HashSet<>();

        for(Polica p : policaService.findAll(korisnik)){
            Set<KnjigaDto> knjigeDto = new HashSet<>();

            for(StavkaPolice sp : stavkaPoliceService.findAll(p)){
                KnjigaDto knjigaDto = new KnjigaDto();
                knjigaDto.setNaslov(sp.getKnjiga().getNaslov());
                knjigaDto.setSlika(sp.getKnjiga().getSlika());
                knjigaDto.setIsbn(sp.getKnjiga().getIsbn());
                knjigeDto.add(knjigaDto);
            }

            PolicaDto policaDto = new PolicaDto(p.getNaziv(), knjigeDto);
            policeDto.add(policaDto);
        }

        return ResponseEntity.ok(policeDto);
    }

    @Transactional
    @DeleteMapping("/polica/{id}")
    public ResponseEntity<Set<PolicaDto>> deletePolica(@PathVariable(name = "id") Long id, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if(korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        if(policaService.findOne(id) == null)
            return new ResponseEntity("Polica ne postoji", HttpStatus.NOT_FOUND);

        if(policaService.findOne(id).isPrimarno())
            return new ResponseEntity("Zabranjeno brisanje primarnih polica", HttpStatus.FORBIDDEN);

        policaService.deleteOne(id);

        Set<PolicaDto> policeDto = new HashSet<>();

        for(Polica p : policaService.findAll(korisnik)){
            Set<KnjigaDto> knjigeDto = new HashSet<>();

            for(StavkaPolice sp : stavkaPoliceService.findAll(p)){
                KnjigaDto knjigaDto = new KnjigaDto();
                knjigaDto.setNaslov(sp.getKnjiga().getNaslov());
                knjigaDto.setSlika(sp.getKnjiga().getSlika());
                knjigaDto.setIsbn(sp.getKnjiga().getIsbn());
                knjigeDto.add(knjigaDto);
            }

            PolicaDto policaDto = new PolicaDto(p.getNaziv(), knjigeDto);
            policeDto.add(policaDto);
        }

        return ResponseEntity.ok(policeDto);
    }

    @PostMapping("/polica/{polica_id}/knjiga/{knjiga_id}")
    public ResponseEntity<Set<PolicaDto>> addKnjiga(@PathVariable(name = "polica_id") Long polica_id, @PathVariable(name = "knjiga_id") Long knjiga_id, HttpSession session){
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

        Set<PolicaDto> policeDto = new HashSet<>();

        for(Polica polica : policaService.findAll(korisnik)){
            Set<KnjigaDto> knjigeDto = new HashSet<>();

            for(StavkaPolice sp : stavkaPoliceService.findAll(polica)){
                KnjigaDto knjigaDto = new KnjigaDto();
                knjigaDto.setNaslov(sp.getKnjiga().getNaslov());
                knjigaDto.setSlika(sp.getKnjiga().getSlika());
                knjigaDto.setIsbn(sp.getKnjiga().getIsbn());
                knjigeDto.add(knjigaDto);
            }

            PolicaDto policaDto = new PolicaDto(polica.getNaziv(), knjigeDto);
            policeDto.add(policaDto);
        }

        return ResponseEntity.ok(policeDto);
    }

    @DeleteMapping("/polica/{polica_id}/knjiga/{knjiga_id}")
    public ResponseEntity<Set<PolicaDto>> deleteKnjiga(@PathVariable(name = "polica_id") Long polica_id, @PathVariable(name = "knjiga_id") Long knjiga_id, HttpSession session){
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

        StavkaPolice stavka = null;

        for(StavkaPolice sp : stavkaPoliceService.findAll(p))
            if(sp.getKnjiga().getId().equals(knjiga_id))
                stavka = sp;

        if(stavka == null)
            return new ResponseEntity("Knjiga ne postoji na trazenoj polici", HttpStatus.NOT_FOUND);

        stavkaPoliceService.delete(stavka.getId());

        if(p.isPrimarno())
            for(Polica polica : policaService.findAll(korisnik))
                for(StavkaPolice sp : stavkaPoliceService.findAll(polica))
                    if(sp.getKnjiga().getId().equals(knjiga_id))
                        stavkaPoliceService.delete(sp.getId());

        Set<PolicaDto> policeDto = new HashSet<>();

        for(Polica polica : policaService.findAll(korisnik)){
            Set<KnjigaDto> knjigeDto = new HashSet<>();

            for(StavkaPolice sp : stavkaPoliceService.findAll(polica)){
                KnjigaDto knjigaDto = new KnjigaDto();
                knjigaDto.setNaslov(sp.getKnjiga().getNaslov());
                knjigaDto.setSlika(sp.getKnjiga().getSlika());
                knjigaDto.setIsbn(sp.getKnjiga().getIsbn());
                knjigeDto.add(knjigaDto);
            }

            PolicaDto policaDto = new PolicaDto(polica.getNaziv(), knjigeDto);
            policeDto.add(policaDto);
        }

        return ResponseEntity.ok(policeDto);
    }
}
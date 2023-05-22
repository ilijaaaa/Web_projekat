package web.project.goodreads.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import web.project.goodreads.dto.PolicaDto;
import web.project.goodreads.entity.Korisnik;
import web.project.goodreads.entity.Polica;
import web.project.goodreads.service.PolicaService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class PolicaRestController {
    @Autowired
    private PolicaService policaService;

    @GetMapping("/police")
    public ResponseEntity<List<Polica>> getPolice() { return ResponseEntity.ok(policaService.findAll()); }
    /*@GetMapping("/police/{naziv}")
    public ResponseEntity<List<Polica>> getPoliceKorisnika(@PathVariable(name = "naziv") String naziv) { return ResponseEntity.ok(policaService.findOne(naziv)); }*/

    @PostMapping("/police/add")
    public ResponseEntity<Polica> addPolica(@RequestBody PolicaDto policaDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if(korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        for(Polica p : policaService.findMany(korisnik))
            if(policaDto.getNaziv().equals(p.getNaziv()))
                return new ResponseEntity("Polica vec postoji", HttpStatus.BAD_REQUEST);

        Polica polica = new Polica(policaDto.getNaziv(), false, korisnik);
        policaService.save(polica);
        return ResponseEntity.ok(polica);
    }

    @Transactional
    @DeleteMapping("/police/delete")
    public ResponseEntity<List<Polica>> deletePolica(@RequestBody PolicaDto policaDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if(korisnik == null)
            return new ResponseEntity("Niste prijavljeni", HttpStatus.FORBIDDEN);

        if(policaService.findOne(policaDto.getNaziv()) == null)
            return new ResponseEntity("Polica ne postoji", HttpStatus.BAD_REQUEST);

        if(policaDto.getNaziv().equals("Want to read") || policaDto.getNaziv().equals("Currently reading") || policaDto.getNaziv().equals("Read"))
            return new ResponseEntity("Zabranjeno brisanje primarnih polica", HttpStatus.FORBIDDEN);

        policaService.deleteOne(policaDto.getNaziv());
        return  ResponseEntity.ok(policaService.findMany(korisnik));
    }
}
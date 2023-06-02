package web.project.goodreads.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.project.goodreads.dto.ZahtevZaAktivacijuDto;
import web.project.goodreads.dto.ZanrDto;
import web.project.goodreads.entity.Korisnik;
import web.project.goodreads.entity.Zanr;
import web.project.goodreads.service.ZanrService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ZanrRestController {
    @Autowired
    private ZanrService zanrService;

    @GetMapping("/zanrovi")
    public ResponseEntity<List<Zanr>> getZanrovi() { return ResponseEntity.ok(zanrService.findAll()); }

    @PostMapping("/dodaj-zanr")
    public ResponseEntity<String> dodajZanr(@RequestBody ZanrDto zanrDto, HttpSession session)
    {
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if ((korisnik == null) || (korisnik.getUloga() != Korisnik.Uloga.ADMINISTRATOR))
            return new ResponseEntity("Admin nije prijavljen", HttpStatus.FORBIDDEN);

        List<Zanr> zanrovi = zanrService.findAll();

        for(Zanr z : zanrovi)
            if(zanrDto.getNaziv().equals(z.getNaziv()))
                return new ResponseEntity("Ovaj zanr vec postoji", HttpStatus.BAD_REQUEST);

        Zanr zanr = new Zanr(zanrDto.getNaziv());
        this.zanrService.save(zanr);

        return ResponseEntity.ok("Uspesno dodat zanr");
    }
}

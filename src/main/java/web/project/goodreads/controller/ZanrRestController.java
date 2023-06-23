package web.project.goodreads.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.project.goodreads.dto.StringDto;
import web.project.goodreads.entity.*;
import web.project.goodreads.service.KorisnikService;
import web.project.goodreads.service.ZanrService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ZanrRestController {
    @Autowired
    private ZanrService zanrService;
    @Autowired
    private KorisnikService korisnikService;

    @GetMapping("/zanrovi")
    public ResponseEntity<List<Zanr>> pregledZanrova(){
        return ResponseEntity.ok(zanrService.findAll());
    }

    @PostMapping("/zanr")
    public ResponseEntity<Zanr> dodajZanr(@RequestBody StringDto stringDto, String sessionId){
        Korisnik korisnik = korisnikService.findBySessionId(sessionId);

        if ((korisnik == null) || (korisnik.getUloga() != Korisnik.Uloga.ADMINISTRATOR))
            return new ResponseEntity("Admin nije prijavljen", HttpStatus.FORBIDDEN);

        for(Zanr z : zanrService.findAll())
            if(stringDto.getValue().equals(z.getNaziv()))
                return new ResponseEntity("Žanr vec postoji", HttpStatus.BAD_REQUEST);

        Zanr zanr = new Zanr(stringDto.getValue());
        this.zanrService.save(zanr);

        return ResponseEntity.ok(zanr);
    }
}

package web.project.goodreads.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.project.goodreads.dto.ZahtevZaAktivacijuDto;
import web.project.goodreads.entity.Autor;
import web.project.goodreads.entity.Korisnik;
import web.project.goodreads.entity.Polica;
import web.project.goodreads.entity.ZahtevZaAktivaciju;
import web.project.goodreads.service.AutorService;
import web.project.goodreads.service.PolicaService;
import web.project.goodreads.service.ZahtevZaAktivacijuService;

import java.util.List;

import static web.project.goodreads.entity.ZahtevZaAktivaciju.RequestStatus.ODBIJENO;
import static web.project.goodreads.entity.ZahtevZaAktivaciju.RequestStatus.ODOBRENO;

@RestController
@RequestMapping("/api")
public class ZahtevZaAktivacijuRestController {
    @Autowired
    private ZahtevZaAktivacijuService zahtevService;
    @Autowired
    private AutorService autorService;

    @Autowired
    private PolicaService policaService;

    @GetMapping("/zahtevi")
    public ResponseEntity<List<ZahtevZaAktivaciju>> getZahtevi(HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if ((korisnik == null) || (korisnik.getUloga() != Korisnik.Uloga.ADMINISTRATOR))
            return new ResponseEntity("Admin nije prijavljen", HttpStatus.FORBIDDEN);

        List<ZahtevZaAktivaciju> zahtevi = zahtevService.findAll();

        if(zahtevi.isEmpty())
            return new ResponseEntity("Nema zahteva", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(zahtevi);
    }

    @PutMapping("/odobri-zahtev")
    public ResponseEntity<String> odobriZahtev(@RequestBody ZahtevZaAktivacijuDto zahtevDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if ((korisnik == null) || (korisnik.getUloga() != Korisnik.Uloga.ADMINISTRATOR))
            return new ResponseEntity("Admin nije prijavljen", HttpStatus.FORBIDDEN);

        List<ZahtevZaAktivaciju> zahtevi = zahtevService.findAll();
        ZahtevZaAktivaciju odobren = null;

        for (ZahtevZaAktivaciju z : zahtevi)
            if(zahtevDto.getId().equals(z.getId()))
                odobren = z;

        if(odobren == null)
            return new ResponseEntity("Ne postoji zahtev", HttpStatus.NOT_FOUND);

        odobren.setStatus(ODOBRENO);
        zahtevService.save(odobren);

        Autor autor = new Autor(zahtevDto.getMejl());   //ne vraca mejl u Postmanu
        this.autorService.save(autor);

        Polica p1 = new Polica("Want to read", true, autor);
        Polica p2 = new Polica("Currently reading", true, autor);
        Polica p3 = new Polica("Read", true, autor);
        policaService.save(p1);
        policaService.save(p2);
        policaService.save(p3);

        return ResponseEntity.ok("Zahtev je odobren");
    }

    @PutMapping("/odbij-zahtev")
    public ResponseEntity<String> odbijZahtev(@RequestBody ZahtevZaAktivacijuDto zahtevDto, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if ((korisnik == null) || (korisnik.getUloga() != Korisnik.Uloga.ADMINISTRATOR))
            return new ResponseEntity("Admin nije prijavljen", HttpStatus.FORBIDDEN);

        List<ZahtevZaAktivaciju> zahtevi = zahtevService.findAll();
        ZahtevZaAktivaciju odbijen = null;

        for (ZahtevZaAktivaciju z : zahtevi)
            if(zahtevDto.getId().equals(z.getId()))
                odbijen = z;

        if(odbijen == null)
            return new ResponseEntity("Ne postoji zahtev", HttpStatus.NOT_FOUND);

        odbijen.setStatus(ODBIJENO);
        zahtevService.save(odbijen);

        return ResponseEntity.ok("Zahtev je odobijen");
    }

}
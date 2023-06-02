package web.project.goodreads.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.project.goodreads.dto.ZahtevZaAktivacijuDto;
import web.project.goodreads.entity.*;
import web.project.goodreads.service.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ZahtevZaAktivacijuRestController {
    @Autowired
    private ZahtevZaAktivacijuService zahtevZaAktivacijuService;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private AutorService autorService;

    @PostMapping("/zahtev/{id}")
    public ResponseEntity<ZahtevZaAktivacijuDto> zahtev(@RequestBody ZahtevZaAktivacijuDto zahtevDto, @PathVariable(name = "id") Long id){
        List<Korisnik> korisnici = korisnikService.findAll();

        for (Korisnik k : korisnici)
            if(zahtevDto.getMejl().equals(k.getMejl()))
                return new ResponseEntity("Zahtev je neuspesan", HttpStatus.BAD_REQUEST);

        Autor autor = autorService.findOne(id);

        if(autor == null)
            return new ResponseEntity("Autor ne postoji", HttpStatus.NOT_FOUND);

        ZahtevZaAktivaciju zahtev = new ZahtevZaAktivaciju(zahtevDto.getMejl(), zahtevDto.getTelefon(), zahtevDto.getPoruka(), autor);
        this.zahtevZaAktivacijuService.save(zahtev);

        return ResponseEntity.ok(zahtevDto);
    }

    @GetMapping("/zahtevi")
    public ResponseEntity<List<ZahtevZaAktivaciju>> getZahtevi(HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if ((korisnik == null) || (korisnik.getUloga() != Korisnik.Uloga.ADMINISTRATOR))
            return new ResponseEntity("Admin nije prijavljen", HttpStatus.FORBIDDEN);

        List<ZahtevZaAktivaciju> zahtevi = zahtevZaAktivacijuService.findAll(ZahtevZaAktivaciju.RequestStatus.CEKANJE);

        if(zahtevi.isEmpty())
            return new ResponseEntity("Nema zahteva", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(zahtevi);
    }
}

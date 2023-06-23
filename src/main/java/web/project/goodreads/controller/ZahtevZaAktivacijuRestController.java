package web.project.goodreads.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import web.project.goodreads.dto.ZahtevZaAktivacijuDto;
import web.project.goodreads.entity.*;
import web.project.goodreads.service.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ZahtevZaAktivacijuRestController {
    @Autowired
    private ZahtevZaAktivacijuService zahtevZaAktivacijuService;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private AutorService autorService;
    @Autowired
    private  PolicaService policaService;
    @Autowired
    private JavaMailSender javaMailSender;


    @PostMapping("/zahtev/{id}")
    public ResponseEntity<ZahtevZaAktivacijuDto> zahtev(@RequestBody ZahtevZaAktivacijuDto zahtevDto, @PathVariable(name = "id") Long id, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        if (korisnik != null)
            return new ResponseEntity("Već imate nalog", HttpStatus.FORBIDDEN);

        for (Korisnik k : korisnikService.findAll())
            if(zahtevDto.getMejl().equals(k.getMejl()))
                return new ResponseEntity("Mejl se već koristi za nalog", HttpStatus.BAD_REQUEST);

        Autor autor = autorService.findOne(id);

        if(autor == null)
            return new ResponseEntity("Autor ne postoji", HttpStatus.NOT_FOUND);

        for(ZahtevZaAktivaciju z : zahtevZaAktivacijuService.findAll(autor))
            if(z.getMejl().equals(zahtevDto.getMejl()))
                return new ResponseEntity("Zahtev već postoji sa tim mejlom", HttpStatus.BAD_REQUEST);

        ZahtevZaAktivaciju zahtev = new ZahtevZaAktivaciju(zahtevDto.getMejl(), zahtevDto.getTelefon(), zahtevDto.getPoruka(), autor);
        this.zahtevZaAktivacijuService.save(zahtev);

        return ResponseEntity.ok(zahtevDto);
    }

    @GetMapping("/zahtevi/{sessionId}")
    public ResponseEntity<List<ZahtevZaAktivaciju>> getZahtevi(@PathVariable(name = "sessionId") String sessionId){
        Korisnik korisnik = korisnikService.findBySessionId(sessionId);

        if ((korisnik == null) || (korisnik.getUloga() != Korisnik.Uloga.ADMINISTRATOR))
            return new ResponseEntity("Admin nije prijavljen", HttpStatus.FORBIDDEN);

        List<ZahtevZaAktivaciju> zahtevi = zahtevZaAktivacijuService.findAll(ZahtevZaAktivaciju.RequestStatus.CEKANJE);

        if(zahtevi.isEmpty())
            return new ResponseEntity("Nema zahteva", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(zahtevi);
    }

    @PutMapping("/zahtev/odobri/{id}")
    public ResponseEntity<ZahtevZaAktivacijuDto> odobriZahtev(@PathVariable(name ="id") Long id, String sessionId){
        Korisnik korisnik = korisnikService.findBySessionId(sessionId);

        if ((korisnik == null) || (korisnik.getUloga() != Korisnik.Uloga.ADMINISTRATOR))
            return new ResponseEntity("Admin nije prijavljen", HttpStatus.FORBIDDEN);

        ZahtevZaAktivaciju zahtevZaAktivaciju = zahtevZaAktivacijuService.findOne(id);

        if(zahtevZaAktivaciju == null)
            return new ResponseEntity("Ne postoji zahtev", HttpStatus.NOT_FOUND);

        if(zahtevZaAktivaciju.getStatus() != ZahtevZaAktivaciju.RequestStatus.CEKANJE)
            return new ResponseEntity("Zahtev je razrešen", HttpStatus.FORBIDDEN);

        zahtevZaAktivaciju.setStatus(ZahtevZaAktivaciju.RequestStatus.ODOBRENO);
        zahtevZaAktivacijuService.save(zahtevZaAktivaciju);

        Autor autor = zahtevZaAktivaciju.getAutor();
        autor.setAktivan(true);
        autor.setMejl(zahtevZaAktivaciju.getMejl());
        autor.setLozinka("123456789");
        this.autorService.save(autor);

        Polica p1 = new Polica("Want to read", true, autor);
        Polica p2 = new Polica("Currently reading", true, autor);
        Polica p3 = new Polica("Read", true, autor);
        policaService.save(p1);
        policaService.save(p2);
        policaService.save(p3);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(zahtevZaAktivaciju.getMejl());
        mailMessage.setSubject("Goodreads zahtev za aktivaciju autora");
        mailMessage.setText("Poštovani,\n" +
                            "Zahtev je odobren.\n\n" +
                            "Mejl: " + zahtevZaAktivaciju.getMejl() +
                            "\nLozinka: 123456789");
        javaMailSender.send(mailMessage);

        ZahtevZaAktivacijuDto zahtevZaAktivacijuDto = new ZahtevZaAktivacijuDto();
        zahtevZaAktivacijuDto.setMejl(zahtevZaAktivaciju.getMejl());
        zahtevZaAktivacijuDto.setPoruka("Zahtev odobren, lozinka: 123456789");
        return ResponseEntity.ok(zahtevZaAktivacijuDto);
    }

    @PutMapping("/zahtev/odbij/{id}")
    public ResponseEntity<ZahtevZaAktivacijuDto> odbijZahtev(@PathVariable(name ="id") Long id, String sessionId){
        Korisnik korisnik = korisnikService.findBySessionId(sessionId);

        if ((korisnik == null) || (korisnik.getUloga() != Korisnik.Uloga.ADMINISTRATOR))
            return new ResponseEntity("Admin nije prijavljen", HttpStatus.FORBIDDEN);

        ZahtevZaAktivaciju zahtevZaAktivaciju = zahtevZaAktivacijuService.findOne(id);

        if(zahtevZaAktivaciju == null)
            return new ResponseEntity("Ne postoji zahtev", HttpStatus.NOT_FOUND);

        if(zahtevZaAktivaciju.getStatus() != ZahtevZaAktivaciju.RequestStatus.CEKANJE)
            return new ResponseEntity("Zahtev je razrešen", HttpStatus.FORBIDDEN);

        zahtevZaAktivaciju.setStatus(ZahtevZaAktivaciju.RequestStatus.ODBIJENO);
        zahtevZaAktivacijuService.save(zahtevZaAktivaciju);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(zahtevZaAktivaciju.getMejl());
        mailMessage.setSubject("Goodreads zahtev za aktivaciju autora");
        mailMessage.setText("Poštovani,\n\n" +
                            "Zahtev je odbijen.");
        javaMailSender.send(mailMessage);

        ZahtevZaAktivacijuDto zahtevZaAktivacijuDto = new ZahtevZaAktivacijuDto();
        zahtevZaAktivacijuDto.setMejl(zahtevZaAktivaciju.getMejl());
        zahtevZaAktivacijuDto.setPoruka("Zahtev odbijen");
        return ResponseEntity.ok(zahtevZaAktivacijuDto);
    }
}

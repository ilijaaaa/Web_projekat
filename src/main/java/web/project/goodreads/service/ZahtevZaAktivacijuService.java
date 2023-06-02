package web.project.goodreads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.project.goodreads.entity.ZahtevZaAktivaciju;
import web.project.goodreads.repository.ZahtevZaAktivacijuRepository;

import java.util.List;

@Service
public class ZahtevZaAktivacijuService {

    @Autowired
    private ZahtevZaAktivacijuRepository zahtevZaAktivacijuRepository;

    public List<ZahtevZaAktivaciju> findAll()
    {
        return zahtevZaAktivacijuRepository.findAll();
    }

    public ZahtevZaAktivaciju save(ZahtevZaAktivaciju zahtev) { return zahtevZaAktivacijuRepository.save(zahtev); }

    /*
    private final JavaMailSender mailSender;

    public ZahtevService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private void posaljiMejlSaLozinkom(String email, String lozinka) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Dobrodošli na našu platformu");
        mailMessage.setText("Vaša nova lozinka je: " + lozinka);

        mailSender.send(mailMessage);
    }
     */

}

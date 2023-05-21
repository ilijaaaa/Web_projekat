package web.project.goodreads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.project.goodreads.entity.ZahtevZaAktivaciju;
import web.project.goodreads.repository.ZahtevZaAktivacijuRepository;

@Service
public class ZahtevZaAktivacijuService {

    @Autowired
    private ZahtevZaAktivacijuRepository zahtevZaAktivacijuRepository;

    public ZahtevZaAktivaciju save(ZahtevZaAktivaciju zahtev) { return zahtevZaAktivacijuRepository.save(zahtev); }

}

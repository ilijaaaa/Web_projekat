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

}

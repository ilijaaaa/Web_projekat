package web.project.goodreads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.project.goodreads.entity.Polica;
import web.project.goodreads.entity.ZahtevZaAktivaciju;
import web.project.goodreads.repository.ZahtevZaAktivacijuRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ZahtevZaAktivacijuService {

    @Autowired
    private ZahtevZaAktivacijuRepository zahtevZaAktivacijuRepository;

    public List<ZahtevZaAktivaciju> findAll()
    {
        return zahtevZaAktivacijuRepository.findAll();
    }

    public List<ZahtevZaAktivaciju> findAll(ZahtevZaAktivaciju.RequestStatus status)
    {
        return zahtevZaAktivacijuRepository.findAllByStatus(status);
    }

    public ZahtevZaAktivaciju findOne(Long id){
        Optional<ZahtevZaAktivaciju> zahtevZaAktivaciju = zahtevZaAktivacijuRepository.findById(id);

        if(zahtevZaAktivaciju.isPresent())
            return zahtevZaAktivaciju.get();

        return null;
    }

    public ZahtevZaAktivaciju save(ZahtevZaAktivaciju zahtev) { return zahtevZaAktivacijuRepository.save(zahtev); }

}

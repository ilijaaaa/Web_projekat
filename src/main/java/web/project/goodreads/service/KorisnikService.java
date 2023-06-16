package web.project.goodreads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.project.goodreads.entity.Korisnik;
import web.project.goodreads.repository.KorisnikRepository;

import java.util.List;
import java.util.Optional;

@Service
public class KorisnikService {
    @Autowired
    private KorisnikRepository korisnikRepository;

    public List<Korisnik> findAll()
    {
        return korisnikRepository.findAll();
    }

    public Korisnik findOne(Long id){
        Optional<Korisnik> korisnik = korisnikRepository.findById(id);
        if (korisnik.isPresent())
            return korisnik.get();
        return null;
    }

    public Korisnik findBySessionId(String sessionId)
    {
        Optional<Korisnik> korisnik = korisnikRepository.findBySessionId(sessionId);
        if(korisnik.isPresent())
            return korisnik.get();
        return null;
    }

    public Korisnik login(String mejl, String lozinka) {
        Korisnik korisnik = korisnikRepository.getByMejl(mejl);
        if(korisnik == null || !korisnik.getLozinka().equals(lozinka))
            return null;
        return  korisnik;
    }

    public Korisnik save(Korisnik korisnik){
        return korisnikRepository.save(korisnik);
    }
}
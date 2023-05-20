package web.project.goodreads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.project.goodreads.entity.Korisnik;
import web.project.goodreads.entity.Polica;
import web.project.goodreads.repository.KorisnikRepository;
import web.project.goodreads.repository.PolicaRepository;

import java.util.List;

@Service
public class KorisnikService {
    @Autowired
    private KorisnikRepository korisnikRepository;

    public List<Korisnik> findAll()
    {
        return korisnikRepository.findAll();
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

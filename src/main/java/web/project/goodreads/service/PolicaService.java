package web.project.goodreads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.project.goodreads.entity.Korisnik;
import web.project.goodreads.entity.Polica;
import web.project.goodreads.repository.PolicaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PolicaService {
    @Autowired
    private  PolicaRepository policaRepository;

    public List<Polica> findAll() { return policaRepository.findAll(); }

    public List<Polica> findAll(Korisnik korisnik) { return policaRepository.findAllByKorisnik(korisnik); }

    public List<Polica> findAll(boolean primarno) { return policaRepository.findByPrimarno(primarno); }

    public Polica findOne(Long id){
        Optional<Polica> polica = policaRepository.findById(id);

        if(polica.isPresent())
            return polica.get();

        return null;
    }

    public void deleteOne(Long id) { policaRepository.deleteById(id);}

    public Polica save(Polica polica) { return policaRepository.save(polica); }
}
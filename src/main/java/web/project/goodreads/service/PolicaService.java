package web.project.goodreads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.project.goodreads.entity.Korisnik;
import web.project.goodreads.entity.Polica;
import web.project.goodreads.repository.PolicaRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PolicaService {
    @Autowired
    private  PolicaRepository policaRepository;

    public List<Polica> findAll() { return policaRepository.findAll(); }

    public List<Polica> findMany(Korisnik korisnik) { return policaRepository.findByKorisnik(korisnik); }

    public Polica findOne(String naziv) { return policaRepository.findByNaziv(naziv); }

    public void deleteOne(String naziv) { policaRepository.deleteByNaziv(naziv);}

    public Polica save(Polica polica) { return policaRepository.save(polica); }
}
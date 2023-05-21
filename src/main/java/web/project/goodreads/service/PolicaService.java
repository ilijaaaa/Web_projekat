package web.project.goodreads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Set<Polica> findOne(Long id){
        Set<Polica> police = new HashSet<>();

        for(Polica p : policaRepository.findAll())
            if(p.getKorisnik().getId() == id)
                police.add(p);

        return police;
    }

    public Polica save(Polica polica) { return policaRepository.save(polica); }
}
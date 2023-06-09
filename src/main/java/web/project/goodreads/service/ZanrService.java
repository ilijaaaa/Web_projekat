package web.project.goodreads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.project.goodreads.entity.Autor;
import web.project.goodreads.entity.Zanr;
import web.project.goodreads.repository.ZanrRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ZanrService {
    @Autowired
    private ZanrRepository zanrRepository;

    public List<Zanr> findAll() { return zanrRepository.findAll(); }

    public Set<Zanr> findOne(String naziv){
        Set<Zanr> zanrovi = new HashSet<>();

        for(Zanr z : zanrRepository.findAll())
            if(z.getNaziv() == naziv)
                    zanrovi.add(z);

        return zanrovi;
    }

    public Zanr save(Zanr zanr) { return zanrRepository.save(zanr); }
}
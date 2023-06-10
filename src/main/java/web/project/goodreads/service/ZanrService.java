package web.project.goodreads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Zanr findOne(String naziv){
        for(Zanr z : zanrRepository.findAll())
            if(z.getNaziv().equals(naziv))
                    return z;

        return null;
    }

    public Zanr save(Zanr zanr) { return zanrRepository.save(zanr); }
}
package web.project.goodreads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.project.goodreads.entity.*;
import web.project.goodreads.repository.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecenzijaService {
    @Autowired
    private RecenzijaRepository recenzijaRepository;
    @Autowired
    private PolicaRepository policaRepository;
    @Autowired
    private StavkaPoliceRepository stavkaPoliceRepository;

    public List<Recenzija> findAll() { return recenzijaRepository.findAll(); }

    public Set<Recenzija> findOne(Long id){
        Set<Recenzija> recenzije = new HashSet<>();

        for(Polica p : policaRepository.findAll())
            for(StavkaPolice s : p.getStavke())
                if(s.getKnjiga().getId() == id)
                    recenzije.add(s.getRecenzija());

        return recenzije;
    }
}
package web.project.goodreads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.project.goodreads.entity.*;
import web.project.goodreads.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class RecenzijaService {
    @Autowired
    private RecenzijaRepository recenzijaRepository;

    public List<Recenzija> findAll() { return recenzijaRepository.findAll(); }

    public Recenzija findOne(Long id){
        Optional<Recenzija> recenzija = recenzijaRepository.findById(id);

        if(recenzija.isPresent())
            return recenzija.get();

        return null;
    }

    public Recenzija save(Recenzija recenzija) { return recenzijaRepository.save(recenzija); }

    public void delete(Long id) { recenzijaRepository.deleteById(id); }
}
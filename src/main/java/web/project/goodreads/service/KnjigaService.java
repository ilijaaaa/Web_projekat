package web.project.goodreads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.project.goodreads.entity.Knjiga;
import web.project.goodreads.entity.Zanr;
import web.project.goodreads.repository.KnjigaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class KnjigaService {
    @Autowired
    private KnjigaRepository knjigaRepository;

    public List<Knjiga> findAll(){
        return knjigaRepository.findAll();
    }

    public Knjiga findOne(Long id){
        Optional<Knjiga> knjiga = knjigaRepository.findById(id);
        if (knjiga.isPresent())
            return knjiga.get();
        return null;
    }

    public Knjiga save(Knjiga knjiga) { return knjigaRepository.save(knjiga); }

    public void deleteOne(Knjiga knjiga) { knjigaRepository.delete(knjiga);}
}
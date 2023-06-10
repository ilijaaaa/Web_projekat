package web.project.goodreads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.project.goodreads.entity.Autor;
import web.project.goodreads.repository.AutorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> findAll() {
        return autorRepository.findAll();
    }

    public Autor findOne(Long id){
        Optional<Autor> autor = autorRepository.findById(id);

        if(autor.isPresent())
            return autor.get();

        return null;
    }

    public Autor save(Autor autor){
        return autorRepository.save(autor);
    }
}

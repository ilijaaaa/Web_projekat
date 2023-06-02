package web.project.goodreads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.project.goodreads.entity.*;
import web.project.goodreads.repository.StavkaPoliceRepository;

import java.util.List;

@Service
public class StavkaPoliceService {
    @Autowired
    private StavkaPoliceRepository stavkaPoliceRepository;

    public List<StavkaPolice> findAll(Knjiga knjiga) { return stavkaPoliceRepository.findAllByKnjiga(knjiga); }

    public List<StavkaPolice> findAll(Polica polica) { return stavkaPoliceRepository.findAllByPolica(polica); }

    public StavkaPolice save(StavkaPolice stavka) { return stavkaPoliceRepository.save(stavka); }

    public void delete(Long id) { stavkaPoliceRepository.deleteById(id); }
}

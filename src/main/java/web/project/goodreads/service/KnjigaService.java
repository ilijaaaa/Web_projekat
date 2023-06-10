package web.project.goodreads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.project.goodreads.entity.Knjiga;
import web.project.goodreads.repository.KnjigaRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class KnjigaService {
    @Autowired
    private KnjigaRepository knjigaRepository;

    public List<Knjiga> findAll() { return knjigaRepository.findAll(); }

    public Set<Knjiga> findAll(String naslov) { return knjigaRepository.findAllByNaslov(naslov); }

    public Knjiga findOne(Long id) { return knjigaRepository.findById(id).orElse(null); }

    public Knjiga save(Knjiga knjiga) { return knjigaRepository.save(knjiga); }

    public void delete(Long id) { knjigaRepository.deleteById(id); }
}
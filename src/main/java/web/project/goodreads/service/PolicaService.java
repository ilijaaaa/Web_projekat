package web.project.goodreads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.project.goodreads.entity.Polica;
import web.project.goodreads.repository.PolicaRepository;

import java.util.List;

@Service
public class PolicaService {
    @Autowired
    private  PolicaRepository policaRepository;

    public List<Polica> findAll() { return policaRepository.findAll(); }

    public Polica save(Polica polica) { return policaRepository.save(polica); }
}

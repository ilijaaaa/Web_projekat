package web.project.goodreads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.project.goodreads.entity.Zanr;
import web.project.goodreads.repository.ZanrRepository;

import java.util.List;

@Service
public class ZanrService {
    @Autowired
    private ZanrRepository zanrRepository;

    public List<Zanr> findAll() { return zanrRepository.findAll(); }
}
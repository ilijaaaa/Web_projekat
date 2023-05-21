package web.project.goodreads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.project.goodreads.entity.Recenzija;
import web.project.goodreads.service.RecenzijaService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class RecenzijaRestController {
    @Autowired
    private RecenzijaService recenzijaService;

    @GetMapping("/recenzije")
    public ResponseEntity<List<Recenzija>> getRecenzije() { return ResponseEntity.ok(recenzijaService.findAll()); }

    @GetMapping("/recenzije/{id}")
    public ResponseEntity<Set<Recenzija>> getRecenzijeKnjige(@PathVariable(name = "id") Long id) { return ResponseEntity.ok(recenzijaService.findOne(id)); }
}

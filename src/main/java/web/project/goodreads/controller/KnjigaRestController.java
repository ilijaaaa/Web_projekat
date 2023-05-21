package web.project.goodreads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.project.goodreads.entity.Knjiga;
import web.project.goodreads.service.KnjigaService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class KnjigaRestController {
    @Autowired
    private KnjigaService knjigaService;

    @GetMapping("/knjige")
    public ResponseEntity<List<Knjiga>> getKnjige(){
        List<Knjiga> knjige = knjigaService.findAll();

        return ResponseEntity.ok(knjige);
    }

    @GetMapping("/knjige/{id}")
    public ResponseEntity<Knjiga> getKnjiga(@PathVariable(name = "id") Long id){
        Knjiga knjiga = knjigaService.findOne(id);

        if(knjiga == null){
            return new ResponseEntity("Knjiga ne postoji", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(knjiga);
    }
}
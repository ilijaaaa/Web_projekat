package web.project.goodreads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.project.goodreads.entity.Zanr;
import web.project.goodreads.service.ZanrService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ZanrRestController {
    @Autowired
    private ZanrService zanrService;

    @GetMapping("/zanrovi")
    public ResponseEntity<List<Zanr>> getZanrovi() { return ResponseEntity.ok(zanrService.findAll()); }
}

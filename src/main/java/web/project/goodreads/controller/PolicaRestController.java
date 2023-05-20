package web.project.goodreads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.project.goodreads.entity.Polica;
import web.project.goodreads.service.PolicaService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PolicaRestController {
    @Autowired
    private PolicaService policaService;

    @GetMapping("/police")
    public ResponseEntity<List<Polica>> getPolice() { return ResponseEntity.ok(policaService.findAll()); }
}

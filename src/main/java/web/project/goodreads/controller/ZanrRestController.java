package web.project.goodreads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.project.goodreads.dto.ZahtevZaAktivacijuDto;
import web.project.goodreads.dto.ZanrDto;
import web.project.goodreads.entity.Zanr;
import web.project.goodreads.service.ZanrService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ZanrRestController {
    @Autowired
    private ZanrService zanrService;

    @GetMapping("/zanrovi")
    public ResponseEntity<Set<ZanrDto>> getZanrovi(){
        List<Zanr> zanrovi = zanrService.findAll();
        Set<ZanrDto> zanroviDto = new HashSet<>();

        for(Zanr z : zanrovi){
            ZanrDto zanrDto = new ZanrDto(z.getNaziv());
            zanroviDto.add(zanrDto);
        }

        return ResponseEntity.ok(zanroviDto);
    }

    @PostMapping("/dodaj-zanr")
    public ResponseEntity<String> dodajZanr(@RequestBody ZanrDto zanrDto)
    {
        List<Zanr> zanrovi = zanrService.findAll();

        for(Zanr z : zanrovi)
            if(zanrDto.getNaziv().equals(z.getNaziv()))
                return new ResponseEntity("Ovaj zanr vec postoji", HttpStatus.BAD_REQUEST);

        Zanr zanr = new Zanr(zanrDto.getNaziv());
        this.zanrService.save(zanr);

        return ResponseEntity.ok("Uspesno dodat zanr");
    }
}

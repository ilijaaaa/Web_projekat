package web.project.goodreads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.project.goodreads.entity.Knjiga;
import web.project.goodreads.entity.Korisnik;
import web.project.goodreads.entity.Polica;

import java.util.List;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long>{
    Korisnik getByMejl(String mejl);

}
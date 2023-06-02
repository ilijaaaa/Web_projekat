package web.project.goodreads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.project.goodreads.entity.Korisnik;
import web.project.goodreads.entity.Polica;

import java.util.List;

public interface PolicaRepository extends JpaRepository<Polica, Long>{
    List<Polica> findAllByKorisnik(Korisnik korisnik);
    List<Polica> findByPrimarno(boolean primarno);

}
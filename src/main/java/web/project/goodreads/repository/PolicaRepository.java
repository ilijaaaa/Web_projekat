package web.project.goodreads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.project.goodreads.entity.Korisnik;
import web.project.goodreads.entity.Polica;

import java.util.List;

public interface PolicaRepository extends JpaRepository<Polica, Long>{
    List<Polica> findByKorisnik(Korisnik korisnik);

    Polica findByNaziv(String naziv);

    void deleteByNaziv(String naziv);

}
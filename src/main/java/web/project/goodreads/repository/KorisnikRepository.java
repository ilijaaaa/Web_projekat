package web.project.goodreads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.project.goodreads.entity.Korisnik;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long>{
    Korisnik getByKorisnickoIme(String username);
}
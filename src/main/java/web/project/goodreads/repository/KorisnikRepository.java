package web.project.goodreads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.project.goodreads.entity.Korisnik;

import java.util.List;
import java.util.Optional;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long>{
    Korisnik getByMejl(String mejl);

    Optional<Korisnik> findBySessionId(String sessionId);
}
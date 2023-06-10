package web.project.goodreads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.project.goodreads.entity.Knjiga;
import java.util.Set;

public interface KnjigaRepository extends JpaRepository<Knjiga, Long>{
    Set<Knjiga> findAllByNaslov(String naslov);
}
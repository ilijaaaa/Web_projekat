package web.project.goodreads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.project.goodreads.entity.Knjiga;

public interface KnjigaRepository extends JpaRepository<Knjiga, Long>{
}
package web.project.goodreads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.project.goodreads.entity.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long>{
}
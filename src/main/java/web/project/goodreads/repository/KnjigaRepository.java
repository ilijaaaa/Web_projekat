package web.project.goodreads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.project.goodreads.entity.Knjiga;

import java.util.List;
import java.util.Optional;

public interface KnjigaRepository extends JpaRepository<Knjiga, Long>{

}
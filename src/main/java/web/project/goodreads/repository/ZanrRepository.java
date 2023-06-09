package web.project.goodreads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.project.goodreads.entity.Zanr;

import java.util.Optional;

public interface ZanrRepository extends JpaRepository<Zanr, Long>{
}
package web.project.goodreads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.project.goodreads.entity.Recenzija;

public interface RecenzijaRepository extends JpaRepository<Recenzija, Long>{
}
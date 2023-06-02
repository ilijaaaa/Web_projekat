package web.project.goodreads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.project.goodreads.entity.*;

import java.util.List;

public interface StavkaPoliceRepository extends JpaRepository<StavkaPolice, Long>{
    List<StavkaPolice> findAllByKnjiga(Knjiga knjiga);
    List<StavkaPolice> findAllByPolica(Polica polica);
}
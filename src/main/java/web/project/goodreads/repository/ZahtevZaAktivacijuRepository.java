package web.project.goodreads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.project.goodreads.entity.*;

import java.util.List;

public interface ZahtevZaAktivacijuRepository extends JpaRepository<ZahtevZaAktivaciju, Long>{
    List<ZahtevZaAktivaciju> findAllByStatus(ZahtevZaAktivaciju.RequestStatus status);

    List<ZahtevZaAktivaciju> findAllByAutor(Autor autor);
}
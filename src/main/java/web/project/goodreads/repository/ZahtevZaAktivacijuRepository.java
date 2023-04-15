package web.project.goodreads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.project.goodreads.entity.ZahtevZaAktivaciju;

public interface ZahtevZaAktivacijuRepository extends JpaRepository<ZahtevZaAktivaciju, Long>{
}
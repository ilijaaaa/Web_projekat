package web.project.goodreads.dto;

import jakarta.servlet.http.HttpSession;
import web.project.goodreads.entity.*;

import java.util.Set;

public class PregledKorisnikaDto {
    private Korisnik korisnik;
    private Set<PolicaDto> police;

    public  PregledKorisnikaDto() {}

    public PregledKorisnikaDto(Korisnik korisnik, Set<PolicaDto> police) {
        this.korisnik = korisnik;
        this.police = police;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Set<PolicaDto> getPolice() {
        return police;
    }

    public void setPolice(Set<PolicaDto> police) {
        this.police = police;
    }
}

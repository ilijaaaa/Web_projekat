package web.project.goodreads.dto;

import web.project.goodreads.entity.*;

import java.util.Set;

public class PregledKorisnikaDto {
    private Korisnik korisnik;
    private Set<Polica> police;

    public  PregledKorisnikaDto() {}

    public PregledKorisnikaDto(Korisnik korisnik, Set<Polica> police) {
        this.korisnik = korisnik;
        this.police = police;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Set<Polica> getPolice() {
        return police;
    }

    public void setPolice(Set<Polica> police) {
        this.police = police;
    }
}

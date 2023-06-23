package web.project.goodreads.dto;

import jakarta.servlet.http.HttpSession;
import web.project.goodreads.entity.*;

import java.util.Set;

public class PregledKorisnikaDto {
    private Korisnik korisnik;
    private Set<PolicaDto> police;
    private String uloga;
    private Boolean aktivan;

    public  PregledKorisnikaDto() {}

    public PregledKorisnikaDto(Korisnik korisnik, Set<PolicaDto> police, String uloga, Boolean aktivan) {
        this.korisnik = korisnik;
        this.police = police;
        this.uloga = uloga;
        this.aktivan = aktivan;
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

    public String getUloga() {
        return uloga;
    }

    public void setUloga(String uloga) {
        this.uloga = uloga;
    }

    public Boolean getAktivan() {
        return aktivan;
    }

    public void setAktivan(Boolean aktivan) {
        this.aktivan = aktivan;
    }
}

package web.project.goodreads.dto;

import web.project.goodreads.entity.Korisnik;

import java.time.LocalDate;

public class SignInDto {
    private String ime, prezime, korisnickoIme, mejl, lozinka, profilnaSlika, opis, ponovljenaLozinka;
    private LocalDate datumRodjenja;
    protected Korisnik.Uloga uloga;

    public SignInDto() {
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public String getMejl() {
        return mejl;
    }

    public String getLozinka() {
        return lozinka;
    }

    public String getProfilnaSlika() {
        return profilnaSlika;
    }

    public String getOpis() {
        return opis;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public Korisnik.Uloga getUloga() {
        return uloga;
    }

    public String getPonovljenaLozinka() { return ponovljenaLozinka; }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public void setMejl(String mejl) {
        this.mejl = mejl;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public void setProfilnaSlika(String profilnaSlika) {
        this.profilnaSlika = profilnaSlika;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public void setUloga(Korisnik.Uloga uloga) {
        this.uloga = uloga;
    }

    public void setPonovljenaLozinka(String ponovljenaLozinka) { this.ponovljenaLozinka = ponovljenaLozinka; }
}
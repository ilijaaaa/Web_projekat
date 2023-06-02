package web.project.goodreads.dto;

import java.time.LocalDate;

public class AzuriranjeKorisnikaDto {
    private String ime, prezime, profilnaSlika, opis, lozinka, staraLozinka, mejl, stariMejl;
    private LocalDate datumRodjenja;

    public AzuriranjeKorisnikaDto() {}

    public AzuriranjeKorisnikaDto(String ime, String prezime, String profilnaSlika, String opis, String lozinka, String staraLozinka, String mejl, String stariMejl, LocalDate datumRodjenja) {
        this.ime = ime;
        this.prezime = prezime;
        this.profilnaSlika = profilnaSlika;
        this.opis = opis;
        this.lozinka = lozinka;
        this.staraLozinka = staraLozinka;
        this.mejl = mejl;
        this.stariMejl = stariMejl;
        this.datumRodjenja = datumRodjenja;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getProfilnaSlika() {
        return profilnaSlika;
    }

    public void setProfilnaSlika(String profilnaSlika) {
        this.profilnaSlika = profilnaSlika;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getStaraLozinka() {
        return staraLozinka;
    }

    public void setStaraLozinka(String staraLozinka) {
        this.staraLozinka = staraLozinka;
    }

    public String getMejl() {
        return mejl;
    }

    public void setMejl(String mejl) {
        this.mejl = mejl;
    }

    public String getStariMejl() {
        return stariMejl;
    }

    public void setStariMejl(String stariMejl) {
        this.stariMejl = stariMejl;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }
}

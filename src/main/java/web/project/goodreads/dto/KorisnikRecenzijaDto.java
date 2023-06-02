package web.project.goodreads.dto;

public class KorisnikRecenzijaDto {
    private String ime, prezime, korisnickoIme, profilnaSlika;

    public KorisnikRecenzijaDto() {
    }

    public KorisnikRecenzijaDto(String ime, String prezime, String korisnickoIme, String profilnaSlika) {
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.profilnaSlika = profilnaSlika;
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

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getProfilnaSlika() {
        return profilnaSlika;
    }

    public void setProfilnaSlika(String profilnaSlika) {
        this.profilnaSlika = profilnaSlika;
    }
}

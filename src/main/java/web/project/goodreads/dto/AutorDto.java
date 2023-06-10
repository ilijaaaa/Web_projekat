package web.project.goodreads.dto;

public class AutorDto {
    private String ime, prezime, slika, opis;

    public AutorDto() {}

    public AutorDto(Long id, String ime, String prezime, String slika, String opis) {
        this.ime = ime;
        this.prezime = prezime;
        this.slika = slika;
        this.opis = opis;
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

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}

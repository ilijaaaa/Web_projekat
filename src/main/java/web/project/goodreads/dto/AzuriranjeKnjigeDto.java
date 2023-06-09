package web.project.goodreads.dto;

import web.project.goodreads.entity.Autor;
import web.project.goodreads.entity.Zanr;

import java.time.LocalDate;

public class AzuriranjeKnjigeDto {
    private String naslov, slika, isbn, opis;
    private LocalDate datum;
    int brStr;
    private Zanr zanr;
    private Autor autor;

    public AzuriranjeKnjigeDto() {}
    public AzuriranjeKnjigeDto(String naslov, String slika, String isbn, String opis, LocalDate datum, int brStr, Zanr zanr, Autor autor) {
        this.naslov = naslov;
        this.slika = slika;
        this.isbn = isbn;
        this.opis = opis;
        this.datum = datum;
        this.brStr = brStr;
        this.zanr = zanr;
        this.autor = autor;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public int getBrStr() {
        return brStr;
    }

    public void setBrStr(int brStr) {
        this.brStr = brStr;
    }

    public Zanr getZanr() {
        return zanr;
    }

    public void setZanr(Zanr zanr) {
        this.zanr = zanr;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}

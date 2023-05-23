package web.project.goodreads.dto;

import web.project.goodreads.entity.Autor;
import web.project.goodreads.entity.Zanr;

import java.math.BigDecimal;
import java.time.LocalDate;

public class KnjigaDto {

    private Long id;
    private String naslov, slika, opis, isbn;
    private LocalDate datum;
    int brStr;
    private BigDecimal ocena;

    private Zanr zanr;

    private Autor autor;

    public KnjigaDto(String naslov, String slika, String opis, String isbn, LocalDate datum, int brStr, Zanr zanr, Autor autor) {
        this.naslov = naslov;
        this.slika = slika;
        this.opis = opis;
        this.isbn = isbn;
        this.brStr = brStr;
        this.datum = datum;
        this.zanr = zanr;
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getBrStr() {
        return brStr;
    }

    public void setBrStr(int brStr) {
        this.brStr = brStr;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
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

package web.project.goodreads.dto;

import java.time.LocalDate;

public class KnjigaDto {
    private String naslov, slika, opis;
    private String isbn;
    private LocalDate datum;
    private int brStr;
    private String zanr;
    private Long autor;

    public KnjigaDto() {}

    public KnjigaDto(String naslov, String slika, String opis, String isbn, LocalDate datum, int brStr, String zanr, Long autor) {
        this.naslov = naslov;
        this.slika = slika;
        this.opis = opis;
        this.isbn = isbn;
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

    public String getZanr() {
        return zanr;
    }

    public void setZanr(String zanr) {
        this.zanr = zanr;
    }

    public Long getAutor() {
        return autor;
    }

    public void setAutor(Long autor) {
        this.autor = autor;
    }
}

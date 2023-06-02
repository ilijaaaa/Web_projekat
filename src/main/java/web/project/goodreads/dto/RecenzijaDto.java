package web.project.goodreads.dto;

import web.project.goodreads.entity.Korisnik;

import java.time.LocalDate;

public class RecenzijaDto {
    private int ocena;
    private String tekst;
    private LocalDate datum;
    private KorisnikRecenzijaDto korisnikRecenzijaDto;

    public RecenzijaDto() {}

    public RecenzijaDto(int ocena, String tekst, LocalDate datum, KorisnikRecenzijaDto korisnikRecenzijaDto) {
        this.ocena = ocena;
        this.tekst = tekst;
        this.datum = datum;
        this.korisnikRecenzijaDto = korisnikRecenzijaDto;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public KorisnikRecenzijaDto getKorisnikRecenzijaDto() {
        return korisnikRecenzijaDto;
    }

    public void setKorisnikRecenzijaDto(KorisnikRecenzijaDto korisnikRecenzijaDto) {
        this.korisnikRecenzijaDto = korisnikRecenzijaDto;
    }
}

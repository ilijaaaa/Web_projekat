package web.project.goodreads.dto;

import java.time.LocalDate;

public class RecenzijaDto {
    private int ocena;
    private String tekst;
    private CharSequence datum;
    public RecenzijaDto() {}

    public RecenzijaDto(int ocena, String tekst, CharSequence datum) {
        this.ocena = ocena;
        this.tekst = tekst;
        this.datum = datum;
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

    public CharSequence getDatum() {
        return datum;
    }

    public void setDatum(CharSequence datum) {
        this.datum = datum;
    }
}

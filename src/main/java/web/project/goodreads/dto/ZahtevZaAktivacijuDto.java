package web.project.goodreads.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ZahtevZaAktivacijuDto {
    private String mejl, telefon, poruka;

    public ZahtevZaAktivacijuDto() {}

    public ZahtevZaAktivacijuDto(String mejl, String telefon, String poruka) {
        this.mejl = mejl;
        this.telefon = telefon;
        this.poruka = poruka;
    }

    public String getMejl() {
        return mejl;
    }

    public void setMejl(String mejl) {
        this.mejl = mejl;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
}

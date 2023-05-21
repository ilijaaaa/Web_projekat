package web.project.goodreads.dto;

public class ZahtevZaAktivacijuDto {
    private String mejl, telefon, poruka;

    ZahtevZaAktivacijuDto() {}

    public ZahtevZaAktivacijuDto(String mejl, String telefon, String poruka) {
        this.mejl = mejl;
        this.telefon = telefon;
        this.poruka = poruka;
    }

    public String getMejl() {
        return mejl;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setMejl(String mejl) {
        this.mejl = mejl;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
}

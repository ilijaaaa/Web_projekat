package web.project.goodreads.dto;

public class ZahtevOdgovorDto {
    private String mejl, poruka;

    public ZahtevOdgovorDto() {}

    public ZahtevOdgovorDto(String mejl, String poruka) {
        this.mejl = mejl;
        this.poruka = poruka;
    }

    public String getMejl() {
        return mejl;
    }

    public void setMejl(String mejl) {
        this.mejl = mejl;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
}

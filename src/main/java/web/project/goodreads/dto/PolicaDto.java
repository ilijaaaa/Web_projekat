package web.project.goodreads.dto;

public class PolicaDto {

    private String naziv;

    public PolicaDto() {
    }

    public PolicaDto(String naziv) {
        this.naziv = naziv;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
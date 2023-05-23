package web.project.goodreads.dto;

public class ZanrDto {
    private String naziv;

    public ZanrDto() {}

    public ZanrDto(String naziv) {
        this.naziv = naziv;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}

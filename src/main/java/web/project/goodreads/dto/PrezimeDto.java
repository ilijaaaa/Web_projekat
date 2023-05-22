package web.project.goodreads.dto;

public class PrezimeDto {
    private String prezime;

    public PrezimeDto(){}

    public PrezimeDto(String prezime) {
        this.prezime = prezime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
}

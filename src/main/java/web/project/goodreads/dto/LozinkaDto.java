package web.project.goodreads.dto;

public class LozinkaDto {
    private String staraLozinka, novaLozinka;

    public LozinkaDto(){}

    public LozinkaDto(String staraLozinka, String novaLozinka) {
        this.staraLozinka = staraLozinka;
        this.novaLozinka = novaLozinka;
    }

    public String getStaraLozinka() {
        return staraLozinka;
    }

    public String getNovaLozinka() {
        return novaLozinka;
    }

    public void setStaraLozinka(String staraLozinka) {
        this.staraLozinka = staraLozinka;
    }

    public void setNovaLozinka(String novaLozinka) {
        this.novaLozinka = novaLozinka;
    }
}

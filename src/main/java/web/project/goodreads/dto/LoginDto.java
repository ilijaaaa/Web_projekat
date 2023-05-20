package web.project.goodreads.dto;

public class LoginDto {

    private String mejl;

    private String lozinka;

    public LoginDto() {
    }

    public LoginDto(String mejl, String lozinka) {
        this.mejl = mejl;
        this.lozinka = lozinka;
    }

    public String getMejl() {
        return mejl;
    }

    public void setMejl(String mejl) {
        this.mejl = mejl;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
}
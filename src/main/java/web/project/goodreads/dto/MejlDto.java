package web.project.goodreads.dto;

public class MejlDto {
    private String stariMejl, noviMejl;

    public MejlDto(){}

    public MejlDto(String stariMejl, String noviMejl) {
        this.stariMejl = stariMejl;
        this.noviMejl = noviMejl;
    }

    public String getStariMejl() {
        return stariMejl;
    }

    public String getNoviMejl() {
        return noviMejl;
    }

    public void setStariMejl(String stariMejl) {
        this.stariMejl = stariMejl;
    }

    public void setNoviMejl(String noviMejl) {
        this.noviMejl = noviMejl;
    }
}

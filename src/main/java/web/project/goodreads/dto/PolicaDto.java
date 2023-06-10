package web.project.goodreads.dto;

import web.project.goodreads.entity.Knjiga;

import java.util.Set;

public class PolicaDto {
    private Long id;
    private String naziv;
    private Set<Knjiga> knjige;

    public PolicaDto() {}

    public PolicaDto(Long id, String naziv, Set<Knjiga> knjige) {
        this.id = id;
        this.naziv = naziv;
        this.knjige = knjige;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Set<Knjiga> getKnjige() {
        return knjige;
    }

    public void setKnjige(Set<Knjiga> knjige) {
        this.knjige = knjige;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
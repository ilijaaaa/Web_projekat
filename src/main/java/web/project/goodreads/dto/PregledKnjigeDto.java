package web.project.goodreads.dto;

import web.project.goodreads.entity.*;

import java.util.Set;

public class PregledKnjigeDto {
    private Knjiga knjiga;
    private Set<Recenzija> recenzije;
    private String uloga;
    private Long id;

    public PregledKnjigeDto() {}

    public PregledKnjigeDto(Knjiga knjiga, Set<Recenzija> recenzije, String uloga, Long id) {
        this.knjiga = knjiga;
        this.recenzije = recenzije;
        this.uloga = uloga;
        this.id = id;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    public Set<Recenzija> getRecenzije() {
        return recenzije;
    }

    public void setRecenzije(Set<Recenzija> recenzije) {
        this.recenzije = recenzije;
    }

    public String getUloga() {
        return uloga;
    }

    public void setUloga(String uloga) {
        this.uloga = uloga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

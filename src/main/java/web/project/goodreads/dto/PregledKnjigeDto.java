package web.project.goodreads.dto;

import web.project.goodreads.entity.*;

import java.util.Set;

public class PregledKnjigeDto {
    private Knjiga knjiga;
    private Set<Recenzija> recenzije;

    public PregledKnjigeDto() {}

    public PregledKnjigeDto(Knjiga knjiga, Set<Recenzija> recenzije) {
        this.knjiga = knjiga;
        this.recenzije = recenzije;
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
}

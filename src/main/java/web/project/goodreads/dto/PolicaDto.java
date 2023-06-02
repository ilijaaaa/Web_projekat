package web.project.goodreads.dto;

import web.project.goodreads.entity.StavkaPolice;

import java.util.Set;

public class PolicaDto {
    private String naziv;
    private Set<KnjigaDto> knjigeDto;

    public PolicaDto() {}

    public PolicaDto(String naziv, Set<KnjigaDto> knjigeDto) {
        this.naziv = naziv;
        this.knjigeDto = knjigeDto;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Set<KnjigaDto> getKnjigeDto() {
        return knjigeDto;
    }

    public void setKnjigeDto(Set<KnjigaDto> knjigeDto) {
        this.knjigeDto = knjigeDto;
    }
}
package web.project.goodreads.dto;

import web.project.goodreads.entity.Knjiga;
import web.project.goodreads.entity.Recenzija;

import java.util.List;
import java.util.Set;

public class PregledKnjigeDto {
    private KnjigaDto knjigaDto;
    private Set<RecenzijaDto> recenzijeDto;

    public PregledKnjigeDto() {}

    public PregledKnjigeDto(KnjigaDto knjigaDto, Set<RecenzijaDto> recenzijeDto) {
        this.knjigaDto = knjigaDto;
        this.recenzijeDto = recenzijeDto;
    }

    public KnjigaDto getKnjigaDto() {
        return knjigaDto;
    }

    public void setKnjigaDto(KnjigaDto knjigaDto) {
        this.knjigaDto = knjigaDto;
    }

    public Set<RecenzijaDto> getRecenzijeDto() {
        return recenzijeDto;
    }

    public void setRecenzijeDto(Set<RecenzijaDto> recenzijeDto) {
        this.recenzijeDto = recenzijeDto;
    }
}

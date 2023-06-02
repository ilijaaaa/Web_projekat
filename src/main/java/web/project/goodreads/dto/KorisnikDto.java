package web.project.goodreads.dto;

import java.util.List;
import java.util.Set;

public class KorisnikDto {
    private KorisnikRecenzijaDto korisnikRecenzijaDto;
    private Set<PolicaDto> policaDto;

    public KorisnikDto() {}

    public KorisnikDto(KorisnikRecenzijaDto korisnikRecenzijaDto, Set<PolicaDto> policaDto) {
        this.korisnikRecenzijaDto = korisnikRecenzijaDto;
        this.policaDto = policaDto;
    }

    public KorisnikRecenzijaDto getKorisnikRecenzijaDto() {
        return korisnikRecenzijaDto;
    }

    public void setKorisnikRecenzijaDto(KorisnikRecenzijaDto korisnikRecenzijaDto) {
        this.korisnikRecenzijaDto = korisnikRecenzijaDto;
    }

    public Set<PolicaDto> getPolicaDto() {
        return policaDto;
    }

    public void setPolicaDto(Set<PolicaDto> policaDto) {
        this.policaDto = policaDto;
    }
}

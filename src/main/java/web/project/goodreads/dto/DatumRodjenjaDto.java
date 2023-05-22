package web.project.goodreads.dto;

import java.time.LocalDate;

public class DatumRodjenjaDto {
    private LocalDate datumRodjenja;

    public DatumRodjenjaDto(){}

    public DatumRodjenjaDto(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }
}

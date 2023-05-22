package web.project.goodreads.dto;

public class ProfilnaSlikaDto {
    private String profilnaSlika;

    public  ProfilnaSlikaDto(){}

    public ProfilnaSlikaDto(String profilnaSlika) {
        this.profilnaSlika = profilnaSlika;
    }

    public String getProfilnaSlika() {
        return profilnaSlika;
    }

    public void setProfilnaSlika(String profilnaSlika) {
        this.profilnaSlika = profilnaSlika;
    }
}

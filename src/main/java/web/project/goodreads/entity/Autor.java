package web.project.goodreads.entity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Entity
@Table(name = "autor")
public class Autor extends Korisnik {
    @Column
    private Boolean aktivan;

    public Autor() {}

    public Autor(Autor a) {
        this.ime = a.ime;
        this.prezime = a.prezime;
        this.korisnickoIme = a.korisnickoIme;
        this.mejl = a.mejl;
        this.lozinka = a.lozinka;
        this.datumRodjenja = a.datumRodjenja;
        this.profilnaSlika = a.profilnaSlika;
        this.opis = a.opis;
        this.uloga = a.uloga;
        this.aktivan = a.aktivan;
    }

    public Boolean getAktivan() {
        return aktivan;
    }

    public void setAktivan(Boolean aktivan) {
        this.aktivan = aktivan;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", korisnickoIme='" + korisnickoIme + '\'' +
                ", mejl='" + mejl + '\'' +
                ", lozinka='" + lozinka + '\'' +
                ", datumRodjenja=" + datumRodjenja +
                ", profilnaSlika='" + profilnaSlika + '\'' +
                ", opis='" + opis + '\'' +
                ", uloga=" + uloga +
                ", aktivan=" + aktivan +
                '}';
    }
}
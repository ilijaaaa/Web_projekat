package web.project.goodreads.entity;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "autor")
public class Autor extends Korisnik {
    @Column
    private Boolean aktivan;
    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private Set<Knjiga> knjige = new HashSet<>();

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
                ", knjige=" + knjige +
                '}';
    }
}
package web.project.goodreads.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Korisnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column
    protected String ime, prezime;
    @Column(unique = true, name = "korisnicko_ime")
    protected String korisnickoIme;
    @Column(unique = true)
    protected String mejl;
    @Column
    protected String lozinka;
    @Column(name = "datum_rodjenja")
    protected LocalDate datumRodjenja;
    @Column(name = "profilna_slika")
    protected String profilnaSlika;
    @Column
    protected String opis;
    @Column
    @Enumerated(EnumType.STRING)
    protected Uloga uloga;
    @OneToMany(mappedBy = "korisnik", fetch = FetchType.EAGER)
    protected Set<Polica> police = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() { return ime; }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getMejl() {
        return mejl;
    }

    public void setMejl(String mejl) {
        this.mejl = mejl;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getProfilnaSlika() {
        return profilnaSlika;
    }

    public void setProfilnaSlika(String profilnaSlika) {
        this.profilnaSlika = profilnaSlika;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }

    public Set<Polica> getPolice() {
        return police;
    }

    public void setPolice(Set<Polica> police) {
        this.police = police;
    }

    @Override
    public String toString() {
        return "Korisnik{" +
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

                '}';
    }

    public enum Uloga {
        CITALAC,
        AUTOR,
        ADMINISTRATOR
    }
}
package web.project.goodreads.entity;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Polica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String naziv;
    @Column
    private boolean primarno;
    @OneToMany(mappedBy = "polica", fetch = FetchType.EAGER)
    private Set<StavkaPolice> stavke = new HashSet<>();
    @ManyToOne
    private Korisnik korisnik;

    public Polica() {}

    public Polica(String naziv, boolean primarno, Korisnik korisnik) {
        this.naziv = naziv;
        this.primarno = primarno;
        this.korisnik = korisnik;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public boolean isPrimarno() {
        return primarno;
    }

    public void setPrimarno(boolean primarno) {
        this.primarno = primarno;
    }

    public Set<StavkaPolice> getStavke() {
        return stavke;
    }

    public void setStavke(Set<StavkaPolice> stavke) {
        this.stavke = stavke;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    @Override
    public String toString() {
        return "Polica{" +
                "id=" + id +
                ", naziv='" + naziv + '\'' +
                ", primarno=" + primarno +
                ", stavke=" + stavke +
                ", korisnik=" + korisnik +
                '}';
    }
}
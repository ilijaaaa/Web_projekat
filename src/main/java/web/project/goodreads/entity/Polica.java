package web.project.goodreads.entity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Entity
public class Polica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String naziv;
    @Column
    private boolean primarno;
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
                ", korisnik=" + korisnik +
                '}';
    }
}
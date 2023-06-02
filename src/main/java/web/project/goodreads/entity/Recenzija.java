package web.project.goodreads.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Recenzija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private int ocena;
    @Column
    private String tekst;
    @Column
    private LocalDate datum;
    @ManyToOne
    private Korisnik korisnik;
    @OneToOne
    private StavkaPolice stavka_police;

    public Recenzija() {}

    public Recenzija(int ocena, String tekst, LocalDate datum, Korisnik korisnik, StavkaPolice stavka_police) {
        this.ocena = ocena;
        this.tekst = tekst;
        this.datum = datum;
        this.korisnik = korisnik;
        this.stavka_police = stavka_police;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public StavkaPolice getStavkaPolice() {
        return stavka_police;
    }

    public void setStavkaPolice(StavkaPolice stavka_police) {
        this.stavka_police = stavka_police;
    }

    @Override
    public String toString() {
        return "Recenzija{" +
                "id=" + id +
                ", ocena=" + ocena +
                ", tekst='" + tekst + '\'' +
                ", datum='" + datum + '\'' +
                ", korisnik=" + korisnik +
                '}';
    }
}

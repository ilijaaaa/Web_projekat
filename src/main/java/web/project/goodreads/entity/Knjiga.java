package web.project.goodreads.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Knjiga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String naslov, slika, opis;
    @Column(unique = true)
    private String isbn;
    @Column
    private LocalDate datum;
    @Column
    private BigDecimal ocena;
    @Column(name="br_str")
    private int brStr;
    @ManyToOne
    private Zanr zanr;
    @ManyToOne
    private Autor autor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public BigDecimal getOcena() {
        return ocena;
    }

    public void setOcena(BigDecimal ocena) {
        this.ocena = ocena;
    }

    public int getBrStr() {
        return brStr;
    }

    public void setBrStr(int brStr) {
        this.brStr = brStr;
    }

    public Zanr getZanr() {
        return zanr;
    }

    public void setZanr(Zanr zanr) {
        this.zanr = zanr;
    }

    @Override
    public String toString() {
        return "Knjiga{" +
                "id=" + id +
                ", naslov='" + naslov + '\'' +
                ", slika='" + slika + '\'' +
                ", opis='" + opis + '\'' +
                ", isbn='" + isbn + '\'' +
                ", datum=" + datum +
                ", ocena=" + ocena +
                ", brStr=" + brStr +
                ", zanr=" + zanr +
                '}';
    }
}
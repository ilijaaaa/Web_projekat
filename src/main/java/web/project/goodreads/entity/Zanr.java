package web.project.goodreads.entity;
import jakarta.persistence.*;

@Entity
public class Zanr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String naziv;

    public Zanr() {}

    public Zanr(Zanr z)
    {
        this.id = z.id;
        this.naziv = z.naziv;
    }

    public Zanr(String naziv) {
        this.naziv = naziv;
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

    @Override
    public String toString() {
        return "Zanr{" +
                "id=" + id +
                ", naziv='" + naziv + '\'' +
                '}';
    }
}

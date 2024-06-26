package web.project.goodreads.entity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Entity
@Table(name = "stavka_police")
public class StavkaPolice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Polica polica;
    @ManyToOne
    private Knjiga knjiga;
    @OneToOne(mappedBy = "stavka_police")
    private Recenzija recenzija;

    public StavkaPolice() {}

    public StavkaPolice(Polica p, Knjiga k){
        polica = p;
        knjiga = k;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Polica getPolica() {
        return polica;
    }

    public void setPolica(Polica polica) {
        this.polica = polica;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    public Recenzija getRecenzija() {
        return recenzija;
    }

    public void setRecenzija(Recenzija recenzija) {
        this.recenzija = recenzija;
    }

    @Override
    public String toString() {
        return "StavkaPolice{" +
                "id=" + id +
                ", recenzija=" + recenzija +
                ", knjiga=" + knjiga +
                '}';
    }
}
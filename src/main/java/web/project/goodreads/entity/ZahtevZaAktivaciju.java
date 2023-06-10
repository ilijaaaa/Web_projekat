package web.project.goodreads.entity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Entity
@Table(name = "zahtev_za_aktivaciju")
public class ZahtevZaAktivaciju {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String mejl, telefon, poruka;
    @Column
    private LocalDate datum;
    @Column
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    @OneToOne
    private Autor autor;

    public ZahtevZaAktivaciju() {}

    public ZahtevZaAktivaciju(String mejl, String telefon, String poruka, Autor autor) {
        this.mejl = mejl;
        this.telefon = telefon;
        this.poruka = poruka;
        this.datum = LocalDate.now();
        this.status = RequestStatus.CEKANJE;
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMejl() {
        return mejl;
    }

    public void setMejl(String mejl) {
        this.mejl = mejl;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "ZahtevZaAktivaciju{" +
                "id=" + id +
                ", mejl='" + mejl + '\'' +
                ", telefon='" + telefon + '\'' +
                ", poruka='" + poruka + '\'' +
                ", datum=" + datum +
                ", status=" + status +
                ", autor=" + autor +
                '}';
    }

    public enum RequestStatus {
        CEKANJE,
        ODOBRENO,
        ODBIJENO
    }
}

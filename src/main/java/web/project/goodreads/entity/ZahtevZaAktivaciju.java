package web.project.goodreads.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

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

    ZahtevZaAktivaciju() {}

    public ZahtevZaAktivaciju(String mejl, String telefon, String poruka) {
        this.mejl = mejl;
        this.telefon = telefon;
        this.poruka = poruka;
        this.datum = LocalDate.now();
        this.status = RequestStatus.CEKANJE;
        autor = null;
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

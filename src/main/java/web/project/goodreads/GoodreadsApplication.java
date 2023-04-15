package web.project.goodreads;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import web.project.goodreads.entity.*;
import web.project.goodreads.repository.*;
import java.util.List;

@SpringBootApplication
public class GoodreadsApplication implements CommandLineRunner {
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private KnjigaRepository knjigaRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private PolicaRepository policaRepository;
    @Autowired
    private RecenzijaRepository recenzijaRepository;
    @Autowired
    private StavkaPoliceRepository stavkaPoliceRepository;
    @Autowired
    private ZahtevZaAktivacijuRepository zahtevZaAktivacijuRepository;
    @Autowired
    private ZanrRepository zanrRepository;

    @Override
    public void run(String... args) {
        List<Korisnik> korisnici = this.korisnikRepository.findAll();
        List<Knjiga> knjige = this.knjigaRepository.findAll();
        List<Zanr> zanrovi = this.zanrRepository.findAll();
        List<Polica> police = this.policaRepository.findAll();
        List<StavkaPolice> stavke = this.stavkaPoliceRepository.findAll();
        List<Recenzija> recenzije = this.recenzijaRepository.findAll();
        List<ZahtevZaAktivaciju> zahtevi = this.zahtevZaAktivacijuRepository.findAll();

        System.out.println("\nKorisnici:");
        for (Korisnik i : korisnici)
            System.out.println(i);
        System.out.println();

        System.out.println("Knjige:");
        for (Knjiga i : knjige)
            System.out.println(i);
        System.out.println();

        System.out.println("Zanrovi:");
        for (Zanr i : zanrovi)
            System.out.println(i);
        System.out.println();

        System.out.println("Police:");
        for (Polica i : police)
            System.out.println(i);
        System.out.println();

        System.out.println("Stavke:");
        for (StavkaPolice i : stavke)
            System.out.println(i);
        System.out.println();

        System.out.println("Recenzije:");
        for (Recenzija i : recenzije)
            System.out.println(i);
        System.out.println();

        System.out.println("Zahtevi za aktivaciju:");
        for (ZahtevZaAktivaciju i : zahtevi)
            System.out.println(i);
    }

    public static void main(String[] args) {
        SpringApplication.run(GoodreadsApplication.class, args);
    }
}

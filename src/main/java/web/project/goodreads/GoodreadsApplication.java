package web.project.goodreads;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GoodreadsApplication implements CommandLineRunner {
    @Override
    public void run(String... args) {
    }

    public static void main(String[] args) {
        SpringApplication.run(GoodreadsApplication.class, args);
    }
}
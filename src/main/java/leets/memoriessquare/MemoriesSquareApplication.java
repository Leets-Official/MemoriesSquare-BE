package leets.memoriessquare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MemoriesSquareApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemoriesSquareApplication.class, args);
    }

}

package cobos.santiago.poc;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PocSpringBatchApplication {
	public static void main(String[] args) {
		SpringApplication.run(PocSpringBatchApplication.class, args);
	}
}

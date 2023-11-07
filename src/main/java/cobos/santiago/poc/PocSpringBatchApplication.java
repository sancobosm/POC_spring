package cobos.santiago.poc;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PocSpringBatchApplication {

	@SneakyThrows
	public static void main(String[] args) {
		SpringApplication.run(PocSpringBatchApplication.class, args);
	}
}

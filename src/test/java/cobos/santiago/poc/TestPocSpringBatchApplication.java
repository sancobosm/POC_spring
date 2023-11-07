package cobos.santiago.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestPocSpringBatchApplication {

	public static void main(String[] args) {
		SpringApplication.from(PocSpringBatchApplication::main).with(TestPocSpringBatchApplication.class).run(args);
	}

}

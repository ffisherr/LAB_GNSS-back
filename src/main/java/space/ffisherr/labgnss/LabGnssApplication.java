package space.ffisherr.labgnss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LabGnssApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabGnssApplication.class, args);
	}

}

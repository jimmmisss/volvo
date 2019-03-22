package br.com.volvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VolvoInit implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(VolvoInit.class, args);
	}
	
	@Override
		public void run(String... args) throws Exception {	
	}

}
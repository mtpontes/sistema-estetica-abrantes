package br.com.karol.sistema;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class AbrantesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbrantesApplication.class, args);
	}
	
	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
  	}
}
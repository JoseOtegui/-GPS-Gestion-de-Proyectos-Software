package grapp.grapp;


import com.mashape.unirest.http.Unirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		Unirest.setTimeouts(0, 0);
		SpringApplication.run(Main.class, args);
	}

}

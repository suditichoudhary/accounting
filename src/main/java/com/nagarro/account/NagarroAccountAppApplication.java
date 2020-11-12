package com.nagarro.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.nagarro.account"})

public class NagarroAccountAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(NagarroAccountAppApplication.class, args);
	}

}

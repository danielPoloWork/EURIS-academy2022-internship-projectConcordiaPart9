package com.euris.academy2022.concordia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.euris.academy2022.concordia.utils.constants.ConcordiaConstant.LOGO_AND_VERSION;

@SpringBootApplication
public class ConcordiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcordiaApplication.class, args);
		System.out.print(LOGO_AND_VERSION);
	}
}
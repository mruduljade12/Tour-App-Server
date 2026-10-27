package com.mrudul.tourandtravel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TourandtravelApplication {

	public static void main(String[] args) {

		System.out.println("MYSQLHOST exists: " +
				(System.getenv("MYSQLHOST") != null));
		System.out.println("MYSQLPORT exists: " +
				(System.getenv("MYSQLPORT") != null));
		System.out.println("MYSQLDATABASE exists: " +
				(System.getenv("MYSQLDATABASE") != null));
		System.out.println("MYSQLUSER exists: " +
				(System.getenv("MYSQLUSER") != null));

		SpringApplication.run(TourandtravelApplication.class, args);
	}

}

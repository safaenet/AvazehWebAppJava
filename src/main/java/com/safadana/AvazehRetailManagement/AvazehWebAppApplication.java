package com.safadana.AvazehRetailManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AvazehWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvazehWebAppApplication.class, args);
		System.out.println("Hi Safa");
	}
}

package com.padroes.java.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class LabPadroesProjetoSpringViaCepApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabPadroesProjetoSpringViaCepApplication.class, args);
	}

}

package com.bank.fx_commission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class FxCommissionApplication {

	public static void main(String[] args) {
		SpringApplication.run(FxCommissionApplication.class, args);
	}
}

package com.bank.shadowledger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ShadowLedgerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShadowLedgerServiceApplication.class, args);
	}
}

package com.jonas.PaymentAccounts;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(servers = @Server(url = "/", description = "Default server url"))
public class PaymentAccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentAccountsApplication.class, args);
	}

}

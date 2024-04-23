package com.MITProjectAdmin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.MITProjectAdmin","com.MITProjectService"})
public class AdminTravelerHelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminTravelerHelperApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(String[] args) {
		return runner -> {
			System.out.println(" hello: Travel Helper Admin application is started Successfully ");
		};
	}

}

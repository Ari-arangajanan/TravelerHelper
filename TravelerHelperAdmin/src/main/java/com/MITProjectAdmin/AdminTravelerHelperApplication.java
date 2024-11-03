package com.MITProjectAdmin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.MITProjectService", "com.MITProjectAdmin"})
@EntityScan(basePackages = {"com.MITProjectService.admin.domain", "com.MITProjectService.bot.domain"})
@EnableJpaRepositories(basePackages = {"com.MITProjectService.admin.dao.JpaRepos", "com.MITProjectService.bot.dao.jpaRepos"})
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

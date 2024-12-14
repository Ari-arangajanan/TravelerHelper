package com.MITProject.TravelerHelperBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.MITProjectService","com.MITProject.TravelerHelperBot"})
@EntityScan(basePackages = {"com.MITProjectService.admin.domain", "com.MITProjectService.bot.domain"})
@EnableJpaRepositories(basePackages = {"com.MITProjectService.admin.dao.JpaRepos", "com.MITProjectService.bot.dao.jpaRepos"})
public class TravelerHelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelerHelperApplication.class, args);
	}

}

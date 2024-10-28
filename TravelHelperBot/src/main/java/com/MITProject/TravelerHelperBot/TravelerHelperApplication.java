package com.MITProject.TravelerHelperBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.MITProject.TravelerHelperBot","com.MITProjectService.bot" })
@EntityScan(basePackages = {"com.MITProjectService.bot.domain"})
@EnableJpaRepositories(basePackages = "com.MITProjectService.bot.dao.jpaRepos")
public class TravelerHelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelerHelperApplication.class, args);
	}

}

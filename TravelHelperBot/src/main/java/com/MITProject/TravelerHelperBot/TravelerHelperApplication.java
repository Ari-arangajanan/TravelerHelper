package com.MITProject.TravelerHelperBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.MITProject.TravelerHelperBot","com.MITProjectService.bot" })
public class TravelerHelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelerHelperApplication.class, args);
	}

}

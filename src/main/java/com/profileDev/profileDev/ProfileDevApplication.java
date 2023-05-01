package com.profileDev.profileDev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProfileDevApplication {
	private static final Logger logger = LoggerFactory.getLogger(ProfileDevApplication.class);		//these logger belongs to import org.slf4j.Logger;

	public static void main(String[] args) {
		// logger.info("this is a info message");		//these logger belongs to import org.slf4j.Logger;
		// logger.warn("this is a warn message");
		// logger.error("this is a error message");
		SpringApplication.run(ProfileDevApplication.class, args);
		logger.info("# ProfileDevApplication started.......................................................");
	}

}

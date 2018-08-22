package com.example.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProjectCouponsApplication implements ApplicationRunner {
	
	
	public static void main(String[] args) {
		SpringApplication.run(ProjectCouponsApplication.class, args);
	}
	
	 @Override
	    public void run(ApplicationArguments applicationArguments) throws Exception {
		 
	    }
	 
}

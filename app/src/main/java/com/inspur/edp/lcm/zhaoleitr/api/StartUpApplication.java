package com.inspur.edp.lcm.zhaoleitr.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class StartUpApplication {
	public static void main(String[] args) {
		SpringApplication.run(StartUpApplication.class,args);
	}
}

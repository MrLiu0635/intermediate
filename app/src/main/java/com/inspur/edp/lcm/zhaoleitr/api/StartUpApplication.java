package com.inspur.edp.lcm.zhaoleitr.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.inspur.edp.lcm")
@EnableJpaRepositories("com.inspur.edp.lcm.zhaoleitr.core.repo")
@EntityScan
public class StartUpApplication {
	public static void main(String[] args) {
		SpringApplication.run(StartUpApplication.class,args);
	}
}

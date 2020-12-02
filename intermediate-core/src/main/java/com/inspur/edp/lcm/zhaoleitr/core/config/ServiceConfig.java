package com.inspur.edp.lcm.zhaoleitr.core.config;

import com.inspur.edp.lcm.zhaoleitr.api.service.MaterialService;
import com.inspur.edp.lcm.zhaoleitr.core.repo.MaterialRepository;
import com.inspur.edp.lcm.zhaoleitr.core.service.MaterialServiceImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.inspur.edp.lcm.zhaoleitr.core.repo")
@EntityScan
public class ServiceConfig {

	@Bean
	public MaterialService getMaterialService(MaterialRepository repository){
		return new MaterialServiceImpl(repository);
	}
}

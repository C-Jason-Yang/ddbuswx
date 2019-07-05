package com.evcas.ddbuswx;

import com.evcas.ddbuswx.common.init.InitUserDefinedPropertyData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class DdbuswxApplication {

	public static void main(String[] args) {
		SpringApplication.run(DdbuswxApplication.class, args);
	}

	@Bean
	public InitUserDefinedPropertyData simDataInit() {
		return new InitUserDefinedPropertyData();
	}

}

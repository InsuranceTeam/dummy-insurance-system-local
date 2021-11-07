package com.insurance.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.insurance.app.mapper")
@SpringBootApplication
public class DummyInsuranceSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DummyInsuranceSystemApplication.class, args);
	}

}

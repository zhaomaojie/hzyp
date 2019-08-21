package com.wzkj.hzyp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.wzkj.hzyp.dao.mybatis")
public class HzypApplication {

	public static void main(String[] args) {
		SpringApplication.run(HzypApplication.class, args);
	}

}

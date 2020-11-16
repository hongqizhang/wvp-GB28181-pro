package com.genersoft.iot.vmp;

import java.util.logging.LogManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.genersoft.iot.vmp")
public class VManageBootstrap extends LogManager {
	public static void main(String[] args) {
		SpringApplication.run(VManageBootstrap.class, args);
	}
}

package com.oyxh.map;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.servlet.ServletComponentScan;

import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.oyxh.map.dao")
@SpringBootApplication()

public class MapStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MapStudyApplication.class, args);
		System.out.println(
				" ______                    _   ______  \n" + "|_   _ \\                  / |_|_   _ `.          \n"
						+ "  | |_) |   .--.    .--. `| |-' | | `. \\  .--.   \n"
						+ "  |  __'. / .'`\\ \\/ .'`\\ \\| |   | |  | |/ .'`\\ \\\n"
						+ " _| |__) || \\__. || \\__. || |, _| |_.' /| \\__. | \n"
						+ "|_______/  '.__.'  '.__.' \\__/|______.'  '.__.'  ");
	}
	
	
	
	
	
}

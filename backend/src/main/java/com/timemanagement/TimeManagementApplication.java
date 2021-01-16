package com.timemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class TimeManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeManagementApplication.class, args);
    }

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/greeting").allowedOrigins("http://localhost:4200");
////						.allowedMethods("PUT", "DELETE","GET", "POST");
//			}
//		};
//	}
}

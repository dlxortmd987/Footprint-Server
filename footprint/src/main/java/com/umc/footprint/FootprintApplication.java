package com.umc.footprint;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
public class FootprintApplication {

	// 설정 파일 사용 (application.yml, aws.yml)
	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.yml,"
			+ "classpath:aws.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(FootprintApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

	@PostConstruct
	public void started(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

}

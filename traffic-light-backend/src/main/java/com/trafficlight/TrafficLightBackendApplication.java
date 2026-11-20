
package com.trafficlight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TrafficLightBackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(
				TrafficLightBackendApplication.class,
				args
		);
	}
}

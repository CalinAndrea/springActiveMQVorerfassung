package com.activemq.ve;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringActiveMqVorerfassungApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(SpringActiveMqVorerfassungApplication.class, args);

		TimeUnit.HOURS.sleep(3);
	}
}

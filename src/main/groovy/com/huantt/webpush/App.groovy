package com.huantt.webpush

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@CompileStatic
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args)
	}
}

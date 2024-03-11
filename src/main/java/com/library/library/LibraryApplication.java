package com.library.library;

import com.library.library.controller.HomeController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
//@EnableJpaRepositories
public class LibraryApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(LibraryApplication.class, args);
	}

}

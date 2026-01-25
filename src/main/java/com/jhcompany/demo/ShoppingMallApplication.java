package com.jhcompany.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShoppingMallApplication {

	public static void main(String[] args) {
        SpringApplication.run(ShoppingMallApplication.class, args);

        String lover = "김말자";
        System.out.println(lover);
	}

}

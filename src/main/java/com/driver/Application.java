package com.driver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
// If the mobile number already exists in the database, the application will throw an exception.
//Create a group by providing a list of users. The list should contain at least 2 users, where the first user is the admin.
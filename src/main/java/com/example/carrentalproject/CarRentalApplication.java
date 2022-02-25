package com.example.carrentalproject;


import com.example.carrentalproject.domain.Role;
import com.example.carrentalproject.dto.UserDto;
import com.example.carrentalproject.repo.RoleRepository;
import com.example.carrentalproject.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;


@SpringBootApplication
@EnableSwagger2
public class CarRentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarRentalApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(UserService userService, RoleRepository roleRepository) {
		return (args) -> {

			roleRepository.save(new Role(1L, "ROLE_USER", new ArrayList<>()));
			userService.saveUser(new UserDto("Tomasz", "Nowak",
				"tomeczek", "Pomidor124", "fenuefd@gmail.com", 2002));

			userService.addRoleToUser("tomeczek", "ROLE_USER");

		};

	}
}


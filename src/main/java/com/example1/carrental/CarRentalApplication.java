package com.example1.carrental;

import com.example1.carrental.constant.FuelType;
import com.example1.carrental.constant.GearBoxType;
import com.example1.carrental.domain.Car;
import com.example1.carrental.domain.CarPackage;
import com.example1.carrental.domain.CarParameters;
import com.example1.carrental.domain.Role;
import com.example1.carrental.dto.UserSaveDto;
import com.example1.carrental.service.CarService;
import com.example1.carrental.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CarRentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarRentalApplication.class, args);
	}

	@Bean
	CommandLineRunner run(CarService carService, UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));

			userService.saveUser(new UserSaveDto("Tomasz", "Nowak", "tomeczek", "pomidor", "tomasz@gmail.com", 555444777));
			userService.addRoleToUser("tomeczek", "ROLE_USER");
			userService.addRoleToUser("tomeczek", "ROLE_MANAGER");
			userService.addRoleToUser("tomeczek", "ROLE_ADMIN");


			CarPackage luxury = new CarPackage(null, "Luxury", 500);
			CarParameters carParameters = new CarParameters(1L, FuelType.PETROL, GearBoxType.AUTOMATIC, 5, 5, true);
			carService.saveCar(new Car(1L, "RSA54633", "Audi", "S8", true, luxury, carParameters));
		};
	}

	}


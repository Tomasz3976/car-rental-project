package com.example1.carrental;

import com.example1.carrental.constant.FuelType;
import com.example1.carrental.constant.GearBoxType;
import com.example1.carrental.domain.CarParameters;
import com.example1.carrental.domain.Role;
import com.example1.carrental.dto.CarDto;
import com.example1.carrental.dto.CarPackageDto;
import com.example1.carrental.dto.UserDto;
import com.example1.carrental.service.CarService;
import com.example1.carrental.service.UserService;
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
	CommandLineRunner run(CarService carService, UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER", new ArrayList<>()));
			userService.saveRole(new Role(null, "ROLE_MANAGER", new ArrayList<>()));
			userService.saveRole(new Role(null, "ROLE_ADMIN", new ArrayList<>()));

			userService.saveUser(new UserDto("Tomasz", "Nowak", "tomeczek", "pomidor", "tomasz@gmail.com", 555444777));
			userService.addRoleToUser("tomeczek", "ROLE_USER");
			userService.addRoleToUser("tomeczek", "ROLE_MANAGER");
			userService.addRoleToUser("tomeczek", "ROLE_ADMIN");


			CarPackageDto luxury = new CarPackageDto("Luxury", 500);
			carService.saveCarPackage(luxury);
			CarParameters carParameters = new CarParameters(null, FuelType.PETROL, GearBoxType.AUTOMATIC, 5, 5, true);
			CarDto car = new CarDto( "RSA54633", "Audi", "S8", true, carParameters);
			carService.saveCar(car, "Luxury");
			CarParameters carParameters2 = new CarParameters(null, FuelType.PETROL, GearBoxType.AUTOMATIC, 5, 5, true);
			CarDto car2 = new CarDto( "WWA67549", "BMW", "M4", true, carParameters2);
			carService.saveCar(car2, "Luxury");
			CarParameters carParameters3 = new CarParameters(null, FuelType.PETROL, GearBoxType.AUTOMATIC, 5, 5, true);
			CarDto car3 = new CarDto( "JHF76548", "Bentley", "Continental", true, carParameters3);
			carService.saveCar(car3, "Luxury");

		};
	}

	}


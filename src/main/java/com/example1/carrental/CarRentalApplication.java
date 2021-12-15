package com.example1.carrental;

import com.example1.carrental.constant.FuelType;
import com.example1.carrental.constant.GearBoxType;
import com.example1.carrental.domain.CarParameters;
import com.example1.carrental.domain.Role;
import com.example1.carrental.dto.CarPackageDto;
import com.example1.carrental.dto.CarSaveDto;
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


			CarPackageDto luxury = new CarPackageDto("Luxury", 500);
			carService.saveCarPackage(luxury);
			CarParameters carParameters = new CarParameters(null, FuelType.PETROL, GearBoxType.AUTOMATIC, 5, 5, true);
			CarSaveDto car = new CarSaveDto( "RSA54633", "Audi", "S8", true, carParameters);
			carService.saveCar(car, "Luxury");
			CarParameters carParameters2 = new CarParameters(null, FuelType.PETROL, GearBoxType.AUTOMATIC, 5, 5, true);
			CarSaveDto car2 = new CarSaveDto( "WWA67549", "BMW", "M4", true, carParameters2);
			carService.saveCar(car2, "Luxury");
			CarParameters carParameters3 = new CarParameters(null, FuelType.PETROL, GearBoxType.AUTOMATIC, 5, 5, true);
			CarSaveDto car3 = new CarSaveDto( "JHF76548", "Bentley", "Continental", true, carParameters3);
			carService.saveCar(car3, "Luxury");

		};
	}

	}


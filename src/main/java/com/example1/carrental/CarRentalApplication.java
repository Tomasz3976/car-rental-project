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



	}


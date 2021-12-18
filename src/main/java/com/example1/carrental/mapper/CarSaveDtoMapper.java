package com.example1.carrental.mapper;

import com.example1.carrental.domain.Car;
import com.example1.carrental.dto.CarDto;
import org.springframework.stereotype.Service;

@Service
public class CarSaveDtoMapper {

        public static Car mapToCar(CarDto carDto) {

                return Car.builder()
                        .registrationNr(carDto.getRegistrationNr())
                        .brand(carDto.getBrand())
                        .model(carDto.getModel())
                        .isAvailable(carDto.getIsAvailable())
                        .carParameters(carDto.getCarParameters())
                        .build();

        }

}

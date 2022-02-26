package com.example.carrentalproject.mapper;

import com.example.carrentalproject.domain.Car;
import com.example.carrentalproject.dto.CarDto;
import org.springframework.stereotype.Service;

@Service
public class CarDtoMapper {

        public static Car mapToCar(CarDto carDto) {

                return Car.builder()
                        .registrationNr(carDto.getRegistrationNr())
                        .brand(carDto.getBrand())
                        .model(carDto.getModel())
                        .isAvailable(carDto.getIsAvailable())
                        .build();

        }

}

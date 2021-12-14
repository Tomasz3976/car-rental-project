package com.example1.carrental.mapper;

import com.example1.carrental.domain.Car;
import com.example1.carrental.dto.CarSaveDto;
import org.springframework.stereotype.Service;

@Service
public class CarSaveDtoMapper {

        public static Car mapToCar(CarSaveDto carSaveDto) {

                return Car.builder()
                        .registrationNr(carSaveDto.getRegistrationNr())
                        .brand(carSaveDto.getBrand())
                        .model(carSaveDto.getModel())
                        .isAvailable(carSaveDto.getIsAvailable())
                        .carParameters(carSaveDto.getCarParameters())
                        .build();

        }

}

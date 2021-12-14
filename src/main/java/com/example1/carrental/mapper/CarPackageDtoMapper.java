package com.example1.carrental.mapper;

import com.example1.carrental.domain.CarPackage;
import com.example1.carrental.dto.CarPackageDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CarPackageDtoMapper {

        public static CarPackage mapToCarPackage(CarPackageDto carPackageDto) {

                return CarPackage.builder()
                        .packageName(carPackageDto.getPackageName())
                        .pricePerHour(carPackageDto.getPricePerHour())
                        .cars(new ArrayList<>())
                        .build();

        }

}

package com.example1.carrental.dto;

import com.example1.carrental.domain.CarParameters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CarEditDto {

        private Long id;
        private String registrationNr;
        private String brand;
        private String model;
        private Boolean isAvailable;
        private CarParameters carParameters;

}

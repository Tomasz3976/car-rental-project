package com.example.carrentalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CarPackageDto {

        private String packageName;
        private Integer pricePerHour;

}

package com.example.carrentalproject.domain;

import com.example.carrentalproject.constant.FuelType;
import com.example.carrentalproject.constant.GearBoxType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "car_parameters")
public class CarParameters {

        @Id
        @JsonIgnore
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", unique = true, nullable = false)
        private Long id;

        @Enumerated(EnumType.STRING)
        @Column(name = "fuel_type", nullable = false)
        private FuelType fuelType;

        @Enumerated(EnumType.STRING)
        @Column(name = "gear_box_type", nullable = false)
        private GearBoxType gearBoxType;

        @Column(name = "number_of_doors", nullable = false)
        private Integer numberOfDoors;

        @Column(name = "number_of_seats", nullable = false)
        private Integer numberOfSeats;

        @Column(name = "is_air_conditioning_available", nullable = false)
        private Boolean isAirConditioningAvailable;

        @JsonIgnore
        @OneToOne(mappedBy = "carParameters")
        private Car car;

}

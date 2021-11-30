package com.example1.carrental.domain;

import com.example1.carrental.constant.FuelType;
import com.example1.carrental.constant.GearBoxType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CarParameters")
public class CarParameters {

        @Id
        @JsonIgnore
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", unique = true, nullable = false)
        private Long id;

        @Enumerated(EnumType.STRING)
        @Column(name = "fuelType", nullable = false)
        private FuelType fuelType;

        @Enumerated(EnumType.STRING)
        @Column(name = "gearBoxType", nullable = false)
        private GearBoxType gearBoxType;

        @Column(name = "numberOfDoors", nullable = false)
        private Integer numberOfDoors;

        @Column(name = "numberOfSeats", nullable = false)
        private Integer numberOfSeats;

        @Column(name = "airConditioningAvailable", nullable = false)
        private Boolean airConditioningAvailable;

}

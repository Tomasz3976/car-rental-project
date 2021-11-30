package com.example1.carrental.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Car")
public class Car {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", unique = true, nullable = false)
        private Long id;

        @Column(name = "registrationNr", nullable = false, length = 8)
        private String registrationNr;

        @Column(name = "brand", nullable = false)
        private String brand;

        @Column(name = "model", nullable = false)
        private String model;

        @Column(name = "isAvailable", nullable = false)
        private Boolean isAvailable;

        @ManyToOne(cascade = CascadeType.ALL)
        private CarPackage carPackage;

        @OneToOne(cascade = CascadeType.ALL)
        private CarParameters carParameters;
}

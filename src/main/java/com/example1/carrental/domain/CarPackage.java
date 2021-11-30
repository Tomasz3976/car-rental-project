package com.example1.carrental.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "CarPackage")
public class CarPackage {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonIgnore
        @Column(name = "id", unique = true, nullable = false)
        private Long id;

        @Column(name = "packageName", nullable = false)
        private String packageName;

        @Column(name = "pricePerHour", nullable = false)
        private Integer pricePerHour;

}

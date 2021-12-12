package com.example1.carrental.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "placedOrder")
public class PlacedOrder {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", unique = true, nullable = false)
        private Long id;

        @Column(name = "userId", nullable = false)
        private Long userId;

        @Column(name = "carId", nullable = false)
        private Long carId;

        @Column(name = "brand", nullable = false)
        private String brand;

        @Column(name = "model", nullable = false)
        private String model;

        @Column(name = "startTime", nullable = false)
        private LocalDateTime startTime;

        @Column(name = "endTime", nullable = false)
        private LocalDateTime endTime;

}

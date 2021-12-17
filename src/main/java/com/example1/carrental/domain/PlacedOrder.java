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
@Table(name = "placed_order")
public class PlacedOrder {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "placed_order_id", unique = true, nullable = false)
        private Long id;

        @Column(name = "user_id", nullable = false)
        private Long userId;

        @Column(name = "car_id", nullable = false)
        private Long carId;

        @Column(name = "brand", nullable = false)
        private String brand;

        @Column(name = "model", nullable = false)
        private String model;

        @Column(name = "start_time", nullable = false)
        private LocalDateTime startTime;

        @Column(name = "end_time", nullable = false)
        private LocalDateTime endTime;

}

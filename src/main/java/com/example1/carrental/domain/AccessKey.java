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
@Table(name = "access_key")
public class AccessKey {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "access_key_id", unique = true, nullable = false)
        private Long id;

        @Column(name = "car_package", nullable = false)
        private String carPackage;

        @Column(name = "hours", nullable = false)
        private Integer hours;

        @OneToOne
        private User user;

}

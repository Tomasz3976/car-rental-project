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
@Table(name = "creditCard")
public class CreditCard {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", unique = true, nullable = false)
        private Long id;

        @Column(name = "cardNumber", unique = true, nullable = false, length = 16)
        private Long cardNumber;

        @Column(name = "month", nullable = false, length = 2)
        private Integer month;

        @Column(name = "year", nullable = false, length = 4)
        private Integer year;

        @Column(name = "CVV", nullable = false, length = 3)
        private Integer CVV;

        @Column(name = "accountBalance", nullable = false)
        private Long accountBalance;

        @OneToOne
        private User user;

}

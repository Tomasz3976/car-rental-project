package com.example.carrentalproject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "credit_card")
public class CreditCard {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", unique = true, nullable = false)
        private Long id;

        @Column(name = "card_number", unique = true, nullable = false, length = 16)
        private Long cardNumber;

        @Column(name = "month", nullable = false, length = 2)
        private Integer month;

        @Column(name = "year", nullable = false, length = 4)
        private Integer year;

        @Column(name = "cvv", nullable = false, length = 3)
        private Integer CVV;

        @Column(name = "account_balance", nullable = false)
        private Long accountBalance;

        @OneToOne
        private User user;

}

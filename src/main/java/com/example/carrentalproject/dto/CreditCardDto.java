package com.example.carrentalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreditCardDto {

    private Long cardNumber;
    private Integer month;
    private Integer year;
    private Integer CVV;

}

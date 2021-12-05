package com.example1.carrental.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditCardDto {

    private Long cardNumber;
    private Integer month;
    private Integer year;
    private Integer CVV;

}

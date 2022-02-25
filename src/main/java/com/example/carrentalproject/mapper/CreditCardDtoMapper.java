package com.example.carrentalproject.mapper;

import com.example.carrentalproject.domain.CreditCard;
import com.example.carrentalproject.dto.CreditCardDto;
import org.springframework.stereotype.Service;

@Service
public class CreditCardDtoMapper {

    public static CreditCard mapToCreditCard(CreditCardDto creditCardDto) {

        return CreditCard.builder()
                .cardNumber(creditCardDto.getCardNumber())
                .month(creditCardDto.getMonth())
                .year(creditCardDto.getYear())
                .CVV(creditCardDto.getCVV())
                .accountBalance(0L)
                .build();
    }

}

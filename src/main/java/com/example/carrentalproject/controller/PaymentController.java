package com.example.carrentalproject.controller;

import com.example.carrentalproject.dto.CreditCardDto;
import com.example.carrentalproject.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

        private final PaymentService paymentService;

        @PostMapping("/payment/addCreditCard")
        public void addCreditCard(@RequestBody CreditCardDto creditCardDto) {
                paymentService.addCreditCard(creditCardDto);
        }

        @PutMapping("/payment/moneyTransfer")
        public void moneyTransfer(@RequestParam Long moneyAmount) {
                paymentService.moneyTransfer(moneyAmount);
        }

}

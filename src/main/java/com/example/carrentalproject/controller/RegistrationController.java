package com.example.carrentalproject.controller;

import com.example.carrentalproject.dto.CreditCardDto;
import com.example.carrentalproject.dto.UserDto;
import com.example.carrentalproject.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

        private final RegistrationService registrationService;

        @PostMapping("/registration/registerUser")
        public void registerUser(@RequestBody UserDto userDto) {
                registrationService.registerUser(userDto);
        }

        @PostMapping("/registration/addCreditCard")
        public void addCreditCard(@RequestBody CreditCardDto creditCardDto) {
                registrationService.addCreditCard(creditCardDto);
        }

        @PutMapping("/registration/moneyTransfer")
        public void moneyTransfer(@RequestParam Long moneyAmount) {
                registrationService.moneyTransfer(moneyAmount);
        }

}

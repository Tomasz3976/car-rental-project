package com.example1.carrental.controller;


import com.example1.carrental.dto.CreditCardDto;
import com.example1.carrental.dto.UserDto;
import com.example1.carrental.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

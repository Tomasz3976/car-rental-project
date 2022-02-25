package com.example.carrentalproject.controller;

import com.example.carrentalproject.dto.UserDto;
import com.example.carrentalproject.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

        private final RegistrationService registrationService;

        @PostMapping("/registration")
        public void registerUser(@RequestBody UserDto userDto) {
                registrationService.registerUser(userDto);
        }

}

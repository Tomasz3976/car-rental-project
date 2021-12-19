package com.example1.carrental.controller;

import com.example1.carrental.domain.Car;
import com.example1.carrental.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeliveryController {

        private final DeliveryService deliveryService;

        @PostMapping("/delivery")
        public Car pickUpTheCar(@RequestParam Long carId) {
                return deliveryService.pickUpTheCar(carId);
        }

}

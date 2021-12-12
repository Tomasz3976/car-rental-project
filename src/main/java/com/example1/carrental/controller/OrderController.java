package com.example1.carrental.controller;

import com.example1.carrental.domain.AccessKey;
import com.example1.carrental.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public AccessKey submitOrder(@RequestParam String carPackage, @RequestParam Integer hours) {
        return orderService.submitOrder(carPackage, hours);
    }

}

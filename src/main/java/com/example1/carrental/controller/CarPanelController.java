package com.example1.carrental.controller;

import com.example1.carrental.domain.Car;
import com.example1.carrental.domain.CarPackage;
import com.example1.carrental.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarPanelController {

        private final CarService carService;

        @GetMapping("/cars/packages")
        public List<CarPackage> getCarPackages() {
                return carService.getCarPackages();
        }

        @GetMapping("/cars/all")
        public List<Car> getAllCars(@RequestParam(required = false) Integer page, Sort.Direction sort) {
                return carService.getAllCars(page, sort);
        }

        @GetMapping("/cars/available")
        public List<Car> getAvailableCars(@RequestParam(required = false) Integer page, Sort.Direction sort) {
                return carService.getAvailableCars(page, sort);
        }

}

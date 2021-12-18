package com.example1.carrental.controller;

import com.example1.carrental.domain.*;
import com.example1.carrental.dto.*;
import com.example1.carrental.mapper.UserDisplayDtoMapper;
import com.example1.carrental.service.CarService;
import com.example1.carrental.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

        private final UserService userService;
        private final CarService carService;

        @GetMapping("/users/all")
        public List<UserDisplayDto> getUsers() {
                return UserDisplayDtoMapper.mapUserToUserDisplayDto(userService.getUsers());
        }

        @PostMapping("/users")
        public UserDto saveUser(@RequestBody UserDto userDto) {
                return userService.saveUser(userDto);
        }

        @PutMapping("/users/{id}")
        public User editUser(@PathVariable Long id, @RequestBody UserDto userDto) {
                return userService.editUser(id, userDto);
        }

        @DeleteMapping("/users/{id}")
        public void deleteUser(@PathVariable Long id) {
                userService.deleteUser(id);
        }

        @PostMapping("/roles")
        public Role saveRole(@RequestBody Role role) {
                return userService.saveRole(role);
        }

        @PutMapping("/users/{username}/roles")
        public User addRoleToUser(@PathVariable String username, @RequestParam String roleName) {
                return userService.addRoleToUser(username, roleName);
        }

        @DeleteMapping("/users/{username}/roles/{roleName}")
        public void deleteUserRole(@PathVariable String username, @PathVariable String roleName) {
                userService.deleteUserRole(username, roleName);
        }

        @PutMapping("/users/{username}/creditCards")
        public User addCreditCardToUser(@PathVariable String username, @RequestBody CreditCardDto creditCardDto) {
                return userService.addCreditCardToUser(username, creditCardDto);
        }

        @DeleteMapping("/users/{username}/creditCards")
        public void deleteUserCreditCard(@PathVariable String username) {
                userService.deleteUserCreditCard(username);
        }

        @GetMapping("/cars/{id}")
        public Car getCar(@PathVariable Long id) {
                return carService.getCar(id);
        }

        @PostMapping("/cars")
        public Car saveCar(@RequestBody CarDto carDto, @RequestParam String packageName) {
                return carService.saveCar(carDto, packageName);
        }

        @PutMapping("/cars/{id}")
        public Car editCar(@PathVariable Long id, @RequestBody CarDto carDto) {
                return carService.editCar(id, carDto);
        }

        @DeleteMapping("/cars/{id}")
        public void deleteCar(@PathVariable Long id) {
                carService.deleteCar(id);
        }

        @PostMapping("/cars/packages")
        public CarPackage saveCarPackage(CarPackageDto carPackageDto) {
                return carService.saveCarPackage(carPackageDto);
        }

        @DeleteMapping("/cars/packages/{id}")
        public void deleteCarPackage(@PathVariable Long id) {
                carService.deleteCarPackage(id);
        }

        @GetMapping("/orders")
        public List<PlacedOrder> getOrders() {
                return carService.getOrders();
        }

}

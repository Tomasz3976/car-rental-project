package com.example1.carrental.controller;

import com.example1.carrental.domain.*;
import com.example1.carrental.dto.UserEditDto;
import com.example1.carrental.dto.UserSaveDto;
import com.example1.carrental.mapper.UserEditDtoMapper;
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

        @GetMapping("/users")
        public List<UserEditDto> getUsers() {
                return UserEditDtoMapper.mapUserToUserDto(userService.getUsers());
        }

        @PostMapping("/users")
        public UserSaveDto saveUser(@RequestBody UserSaveDto userSaveDto) {
                return userService.saveUser(userSaveDto);
        }

        @PutMapping("/users")
        public User editUser(@RequestBody UserEditDto userEditDto) {
                return userService.editUser(userEditDto);
        }

        @DeleteMapping("/users/{id}")
        public void deleteUser(@PathVariable Long id) {
                userService.deleteUser(id);
        }

        @PostMapping("/users/roles")
        public Role saveRole(@RequestBody Role role) {
                return userService.saveRole(role);
        }

        @PutMapping("/users/roles")
        public User addRoleToUser(@RequestParam String username, @RequestParam String roleName) {
                return userService.addRoleToUser(username, roleName);
        }

        @DeleteMapping("/users/roles")
        public void deleteUserRole(@RequestParam String username, @RequestParam String roleName) {
                userService.deleteUserRole(username, roleName);
        }

        @PutMapping("/users/creditCards")
        public User addCreditCardToUser(@RequestParam String username, @RequestBody CreditCard creditCard) {
                return userService.addCreditCardToUser(username, creditCard);
        }

        @DeleteMapping("/users/creditCards")
        public void deleteUserCreditCard(@RequestParam String username) {
                userService.deleteUserCreditCard(username);
        }

        @GetMapping("/cars/{id}")
        public Car getCar(@PathVariable Long id) {
                return carService.getCar(id);
        }

        @PostMapping("/cars")
        public Car saveCar(@RequestBody Car car) {
                return carService.saveCar(car);
        }

        @PutMapping("/cars")
        public Car editCar(@RequestBody Car car) {
                return carService.editCar(car);
        }

        @DeleteMapping("/cars/{id}")
        public void deleteCar(@PathVariable Long id) {
                carService.deleteCar(id);
        }

        @PostMapping("/cars/packages")
        public CarPackage saveCarPackage(CarPackage carPackage) {
                return carService.saveCarPackage(carPackage);
        }

        @PutMapping("/cars/packages")
        public CarPackage editCarPackage(CarPackage carPackage) {
                return carService.editCarPackage(carPackage);
        }

        @DeleteMapping("/cars/packages/{id}")
        public void deleteCarPackage(@PathVariable Long id) {
                carService.deleteCarPackage(id);
        }

}

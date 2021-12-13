package com.example1.carrental.service;

import com.example1.carrental.domain.*;
import com.example1.carrental.exception.InvalidPackageException;
import com.example1.carrental.exception.NoAccessKeyException;
import com.example1.carrental.exception.UnavailableCarException;
import com.example1.carrental.repo.CarRepo;

import com.example1.carrental.repo.OrderRepo;
import com.example1.carrental.security.LoggedInUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

        @Mock
        CarRepo carRepo;

        @Mock
        OrderRepo orderRepo;

        @Mock
        LoggedInUser loggedInUser;

        @InjectMocks
        DeliveryService deliveryService;


        @Test
        void itShouldRentACar() throws AccessDeniedException {
                CarPackage sporty = CarPackage.builder().packageName("Sporty").pricePerHour(300).build();
                Car car = Car.builder().id(1L).brand("Audi").model("RS3").isAvailable(true).carPackage(sporty).build();
                AccessKey accessKey = AccessKey.builder().carPackage("Sporty").hours(2).build();
                User user = User.builder().username("BlackJohn32").accessKey(accessKey).build();


                when(carRepo.findById(1L)).thenReturn(Optional.of(car));
                when(loggedInUser.getUser()).thenReturn(user);

                assertThat(deliveryService.pickUpTheCar(1L)).isEqualTo(car);
                assertThat(car.getIsAvailable()).isFalse();
        }

        @Test
        void itShouldThrowEntityNotFoundException() {
                when(carRepo.findById(1L)).thenReturn(Optional.empty());

                assertThrows(EntityNotFoundException.class, () -> deliveryService.pickUpTheCar(1L));
        }

        @Test
        void itShouldThrowNoAccessKeyException() {
                Car car = Car.builder().id(1L).brand("Honda").model("Civic").build();
                User user = User.builder().username("MaxVerstappen").accessKey(null).build();

                when(carRepo.findById(1L)).thenReturn(Optional.of(car));
                when(loggedInUser.getUser()).thenReturn(user);

                assertThrows(NoAccessKeyException.class, () -> deliveryService.pickUpTheCar(1L));
        }

        @Test
        void itShouldThrowInvalidPackageException() {
                CarPackage carPackage = CarPackage.builder().packageName("Sporty").pricePerHour(300).build();
                Car car = Car.builder().id(3L).brand("Fiat").model("Multipla").carPackage(carPackage).build();
                AccessKey accessKey = AccessKey.builder().carPackage("Luxury").hours(3).build();
                User user = User.builder().username("JohnMcChicken").accessKey(accessKey).build();

                when(carRepo.findById(3L)).thenReturn(Optional.of(car));
                when(loggedInUser.getUser()).thenReturn(user);

                assertThrows(InvalidPackageException.class, () -> deliveryService.pickUpTheCar(3L));
        }

        @Test
        void itShouldThrowUnavailableCarException() {
                CarPackage carPackage = CarPackage.builder().packageName("Ordinary").pricePerHour(100).build();
                Car car = Car.builder().id(2L).brand("Peugeot").model("206").isAvailable(false).carPackage(carPackage).build();
                AccessKey accessKey = AccessKey.builder().carPackage("Ordinary").hours(7).build();
                User user = User.builder().username("JulietBB56").accessKey(accessKey).build();

                when(carRepo.findById(2L)).thenReturn(Optional.of(car));
                when(loggedInUser.getUser()).thenReturn(user);

                assertThrows(UnavailableCarException.class, () -> deliveryService.pickUpTheCar(2L));
        }

}
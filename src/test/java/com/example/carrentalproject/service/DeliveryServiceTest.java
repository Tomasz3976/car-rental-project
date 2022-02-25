package com.example.carrentalproject.service;

import com.example.carrentalproject.domain.AccessKey;
import com.example.carrentalproject.domain.Car;
import com.example.carrentalproject.domain.CarPackage;
import com.example.carrentalproject.domain.PlacedOrder;
import com.example.carrentalproject.domain.User;
import com.example.carrentalproject.exception.InvalidPackageException;
import com.example.carrentalproject.exception.NoAccessKeyException;
import com.example.carrentalproject.exception.UnavailableCarException;
import com.example.carrentalproject.repo.AccessKeyRepo;
import com.example.carrentalproject.repo.CarRepo;
import com.example.carrentalproject.repo.OrderRepo;
import com.example.carrentalproject.security.LoggedInUser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

        @Mock
        CarRepo carRepo;

        @Mock
        OrderRepo orderRepo;

        @Mock
        AccessKeyRepo accessKeyRepo;

        @Mock
        LoggedInUser loggedInUser;

        @InjectMocks
        DeliveryService deliveryService;


        @Test
        void itShouldRentACar() {
                CarPackage sporty = CarPackage.builder()
                        .packageName("Sporty")
                        .pricePerHour(300)
                        .build();

                Car car = Car.builder()
                        .id(1L)
                        .brand("Audi")
                        .model("RS3")
                        .isAvailable(true)
                        .carPackage(sporty)
                        .build();

                AccessKey accessKey = AccessKey.builder()
                        .carPackage("Sporty")
                        .hours(2)
                        .build();

                User user = User.builder()
                        .id(1L)
                        .username("BlackJohn32")
                        .accessKey(accessKey)
                        .build();

                PlacedOrder placedOrder = PlacedOrder.builder()
                        .userId(user.getId())
                        .carId(car.getId())
                        .brand(car.getBrand())
                        .model(car.getModel())
                        .build();


                when(carRepo.findById(1L)).thenReturn(Optional.of(car));
                when(loggedInUser.getUser()).thenReturn(user);
                doNothing().when(accessKeyRepo).delete(user.getAccessKey());


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
                Car car = Car.builder()
                        .id(1L)
                        .brand("Honda")
                        .model("Civic")
                        .build();

                User user = User.builder()
                        .username("MaxVerstappen")
                        .accessKey(null)
                        .build();


                when(carRepo.findById(1L)).thenReturn(Optional.of(car));
                when(loggedInUser.getUser()).thenReturn(user);


                assertThrows(NoAccessKeyException.class, () -> deliveryService.pickUpTheCar(1L));
        }

        @Test
        void itShouldThrowInvalidPackageException() {
                CarPackage carPackage = CarPackage.builder()
                        .packageName("Sporty")
                        .pricePerHour(300)
                        .build();

                Car car = Car.builder()
                        .id(3L)
                        .brand("Fiat")
                        .model("Multipla")
                        .carPackage(carPackage)
                        .build();

                AccessKey accessKey = AccessKey.builder()
                        .carPackage("Luxury")
                        .hours(3)
                        .build();

                User user = User.builder()
                        .username("JohnMcChicken")
                        .accessKey(accessKey)
                        .build();


                when(carRepo.findById(3L)).thenReturn(Optional.of(car));
                when(loggedInUser.getUser()).thenReturn(user);


                assertThrows(InvalidPackageException.class, () -> deliveryService.pickUpTheCar(3L));
        }

        @Test
        void itShouldThrowUnavailableCarException() {
                CarPackage carPackage = CarPackage.builder()
                        .packageName("Ordinary")
                        .pricePerHour(100)
                        .build();

                Car car = Car.builder()
                        .id(2L)
                        .brand("Peugeot")
                        .model("206")
                        .isAvailable(false)
                        .carPackage(carPackage)
                        .build();

                AccessKey accessKey = AccessKey.builder()
                        .carPackage("Ordinary")
                        .hours(7)
                        .build();

                User user = User.builder()
                        .username("JulietBB56")
                        .accessKey(accessKey)
                        .build();


                when(carRepo.findById(2L)).thenReturn(Optional.of(car));
                when(loggedInUser.getUser()).thenReturn(user);


                assertThrows(UnavailableCarException.class, () -> deliveryService.pickUpTheCar(2L));
        }

}
package com.example1.carrental.service;

import com.example1.carrental.domain.AccessKey;
import com.example1.carrental.domain.CarPackage;
import com.example1.carrental.domain.CreditCard;
import com.example1.carrental.domain.User;
import com.example1.carrental.dto.AccessKeyDto;
import com.example1.carrental.exception.InsufficientFundsException;
import com.example1.carrental.exception.NoCreditCardException;
import com.example1.carrental.mapper.AccessKeyDtoMapper;
import com.example1.carrental.repo.AccessKeyRepo;
import com.example1.carrental.repo.CarPackageRepo;
import com.example1.carrental.security.LoggedInUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

        @Mock
        CarPackageRepo carPackageRepo;

        @Mock
        AccessKeyRepo accessKeyRepo;

        @Mock
        LoggedInUser loggedInUser;

        @InjectMocks
        OrderService orderService;


        @Test
        void itShouldReturnAccessKeyDto() {
                CreditCard card = CreditCard.builder()
                        .cardNumber(7755443334559900L)
                        .month(4)
                        .year(2023)
                        .CVV(278)
                        .accountBalance(1200L)
                        .build();

                User user = User.builder()
                        .creditCard(card)
                        .build();

                CarPackage luxury = CarPackage.builder()
                        .packageName("Luxury")
                        .pricePerHour(500)
                        .build();

                AccessKey accessKey = AccessKey.builder()
                        .carPackage("Luxury")
                        .hours(2)
                        .build();

                AccessKeyDto accessKeyDto = AccessKeyDto.builder()
                        .carPackage("Luxury")
                        .hours(2)
                        .build();


                when(loggedInUser.getUser()).thenReturn(user);
                when(carPackageRepo.findByPackageName("Luxury")).thenReturn(Optional.of(luxury));
                when(accessKeyRepo.save(accessKey)).thenReturn(accessKey);


                assertThat(orderService.submitOrder("Luxury", 2)).isEqualTo(accessKeyDto);
                assertThat(user.getCreditCard().getAccountBalance()).isEqualTo(200L);
        }

        @Test
        void itShouldThrowEntityNotFoundException() {
                CreditCard card = CreditCard.builder()
                        .accountBalance(0L)
                        .build();

                User user = User.builder()
                        .username("Radoslaw")
                        .creditCard(card)
                        .build();


                when(loggedInUser.getUser()).thenReturn(user);
                when(carPackageRepo.findByPackageName(anyString())).thenThrow(EntityNotFoundException.class);


                assertThrows(EntityNotFoundException.class, () -> orderService.submitOrder("BigCar", 3));
        }

        @Test
        void itShouldThrowNoCreditCardException() {
                User user = User.builder()
                        .username("Tomasz")
                        .creditCard(null)
                        .build();


                when(loggedInUser.getUser()).thenReturn(user);


                assertThrows(NoCreditCardException.class, () -> orderService.submitOrder("Ordinary", 2));
        }

        @Test
        void itShouldThrowInsufficientFundsException() {
                CreditCard card = CreditCard.builder()
                        .accountBalance(600L)
                        .build();

                User user = User.builder()
                        .username("Radoslaw")
                        .creditCard(card)
                        .build();

                CarPackage luxury = CarPackage.builder()
                        .packageName("Luxury")
                        .pricePerHour(500)
                        .build();


                when(loggedInUser.getUser()).thenReturn(user);
                when(carPackageRepo.findByPackageName("Luxury")).thenReturn(Optional.of(luxury));


                assertThrows(InsufficientFundsException.class, () -> orderService.submitOrder("Luxury", 2));
        }

}
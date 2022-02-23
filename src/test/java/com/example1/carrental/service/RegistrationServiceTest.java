package com.example1.carrental.service;

import com.example1.carrental.domain.CreditCard;
import com.example1.carrental.domain.User;
import com.example1.carrental.dto.CreditCardDto;
import com.example1.carrental.dto.UserDto;
import com.example1.carrental.exception.ExistingEntityException;
import com.example1.carrental.exception.NoCreditCardException;
import com.example1.carrental.exception.WeakPasswordException;
import com.example1.carrental.repo.CreditCardRepo;
import com.example1.carrental.repo.UserRepo;
import com.example1.carrental.security.LoggedInUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

        @Mock
        UserRepo userRepo;

        @Mock
        CreditCardRepo creditCardRepo;

        @Mock
        LoggedInUser loggedInUser;

        @InjectMocks
        RegistrationService registrationService;


        @Test
        void itShouldAddCreditCardToUser() {
                User user = User.builder()
                        .firstName("Mickey")
                        .lastName("Rourke")
                        .build();

                CreditCardDto creditCardDto = CreditCardDto.builder()
                        .cardNumber(8888943300781111L)
                        .build();

                CreditCard creditCard = CreditCard.builder()
                        .cardNumber(8888943300781111L)
                        .accountBalance(0L)
                        .build();


                when(loggedInUser.getUser()).thenReturn(user);
                when(creditCardRepo.save(creditCard)).thenReturn(creditCard);


                registrationService.addCreditCard(creditCardDto);

                assertThat(user.getCreditCard()).isEqualTo(creditCard);
        }

        @Test
        void itShouldMakeMoneyTransfer() {
                CreditCard creditCard = CreditCard.builder()
                        .accountBalance(0L)
                        .build();

                User user = User.builder()
                        .firstName("Richard")
                        .lastName("Hammond")
                        .creditCard(creditCard)
                        .build();


                when(loggedInUser.getUser()).thenReturn(user);


                registrationService.moneyTransfer(700L);

                assertThat(user.getCreditCard().getAccountBalance()).isEqualTo(700L);
        }

        @Test
        void itShouldThrowExistingEntityException() {
                UserDto userDto = UserDto.builder()
                        .username("GreenJohn78")
                        .build();

                User user = User.builder()
                        .username("GreenJohn78")
                        .build();


                when(userRepo.findByUsername(userDto.getUsername())).thenReturn(Optional.of(user));


                assertThrows(ExistingEntityException.class, () -> registrationService.registerUser(userDto));
        }

        @Test
        void itShouldThrowWeakPasswordException() {
                UserDto userDto = UserDto.builder()
                        .username("JohnBDP685")
                        .password("johnapple56")
                        .build();


                when(userRepo.findByUsername(userDto.getUsername())).thenReturn(Optional.empty());


                assertThrows(WeakPasswordException.class, () -> registrationService.registerUser(userDto));
        }

        @Test
        void itShouldThrowNoCreditCardException() {
                User user = User.builder()
                        .username("MeekMill765")
                        .creditCard(null)
                        .build();

                when(loggedInUser.getUser()).thenReturn(user);

                assertThrows(NoCreditCardException.class, () -> registrationService.moneyTransfer(400L));
        }

}
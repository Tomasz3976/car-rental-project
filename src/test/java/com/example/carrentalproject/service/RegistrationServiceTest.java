package com.example.carrentalproject.service;

import com.example.carrentalproject.domain.User;
import com.example.carrentalproject.dto.UserDto;
import com.example.carrentalproject.exception.ExistingEntityException;
import com.example.carrentalproject.exception.WeakPasswordException;
import com.example.carrentalproject.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

        @Mock
        UserRepository userRepository;

        @InjectMocks
        RegistrationService registrationService;

        @Test
        void itShouldThrowExistingEntityException() {
                UserDto userDto = UserDto.builder()
                        .username("GreenJohn78")
                        .build();

                User user = User.builder()
                        .username("GreenJohn78")
                        .build();


                when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Optional.of(user));


                assertThrows(ExistingEntityException.class, () -> registrationService.registerUser(userDto));
        }

        @Test
        void itShouldThrowWeakPasswordException() {
                UserDto userDto = UserDto.builder()
                        .username("JohnBDP685")
                        .password("johnapple56")
                        .build();


                when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Optional.empty());


                assertThrows(WeakPasswordException.class, () -> registrationService.registerUser(userDto));
        }

}
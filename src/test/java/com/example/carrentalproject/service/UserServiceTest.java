package com.example.carrentalproject.service;

import com.example.carrentalproject.domain.CreditCard;
import com.example.carrentalproject.domain.Role;
import com.example.carrentalproject.domain.User;
import com.example.carrentalproject.dto.CreditCardDto;
import com.example.carrentalproject.dto.UserInDto;
import com.example.carrentalproject.exception.AssignedRoleException;
import com.example.carrentalproject.exception.ExistingEntityException;
import com.example.carrentalproject.mapper.UserDtoMapper;
import com.example.carrentalproject.repository.CreditCardRepository;
import com.example.carrentalproject.repository.RoleRepository;
import com.example.carrentalproject.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

        @Mock
        UserRepository userRepository;

        @Mock
        RoleRepository roleRepository;

        @Mock
        CreditCardRepository creditCardRepository;

        @Mock
        PasswordEncoder passwordEncoder;

        @InjectMocks
        UserService userService;


        @Test
        void itShouldSaveUser() {
                UserInDto userInDto = UserInDto.builder()
                        .firstName("Sebastian")
                        .lastName("Alvarez")
                        .username("sebix")
                        .password("Marbella465")
                        .email("apple@gamil.com")
                        .phone(968956584)
                        .build();

                User user = User.builder()
                        .firstName("Sebastian")
                        .lastName("Alvarez")
                        .username("sebix")
                        .email("apple@gamil.com")
                        .phone(968956584)
                        .roles(new ArrayList<>())
                        .build();


                when(userRepository.findByUsername("sebix")).thenReturn(Optional.empty());
                when(userRepository.save(user)).thenReturn(user);


                userService.saveUser(userInDto);

                assertThat(user.getFirstName()).isEqualTo(userInDto.getFirstName());
                assertThat(user.getEmail()).isEqualTo(userInDto.getEmail());
        }

        @Test
        void itShouldCheckIfUserIsEdited() {
                Long id = 2L;

                UserInDto userInDto = UserInDto.builder()
                        .username("Amigo")
                        .build();

                User user = User.builder()
                        .id(2L)
                        .build();


                when(userRepository.findById(id)).thenReturn(Optional.of(user));
                when(userRepository.save(user)).thenReturn(user);


                Assertions.assertThat(userService.editUser(id, userInDto)).isEqualTo(user);
        }

        @Test
        void itShouldDeleteUser() {
                User user = User.builder()
                        .id(6L)
                        .build();


                when(userRepository.existsById(6L)).thenReturn(true);
                doNothing().when(userRepository).deleteById(6L);


                userService.deleteUser(6L);

                verify(userRepository, times(1)).deleteById(6L);
        }

        @Test
        void itShouldSaveRole() {
                Role role = Role.builder()
                        .name("ROLE_MANAGER")
                        .build();


                when(roleRepository.findByName("ROLE_MANAGER")).thenReturn(Optional.empty());
                when(roleRepository.save(role)).thenReturn(role);


                Assertions.assertThat(userService.saveRole(role)).isEqualTo(role);
        }

        @Test
        void itShouldAddRoleToUser() {
                User user = User.builder()
                        .firstName("Pawel")
                        .lastName("Mroczek")
                        .username("pablo")
                        .roles(new ArrayList<>())
                        .build();

                Role role = Role.builder()
                        .name("ROLE_ADMIN")
                        .users(new ArrayList<>())
                        .build();


                when(userRepository.findByUsername("pablo")).thenReturn(Optional.of(user));
                when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(role));
                when(userRepository.save(user)).thenReturn(user);


                userService.addRoleToUser("pablo", "ROLE_ADMIN");

                assertThat(user.getRoles()).hasSize(1);
        }

        @Test
        void itShouldDeleteUserRole() {
                User user = User.builder()
                        .firstName("Kamil")
                        .lastName("Jasny")
                        .username("Kamilek")
                        .roles(new ArrayList<>())
                        .build();

                Role role = Role.builder()
                        .name("ROLE_USER")
                        .users(new ArrayList<>())
                        .build();


                when(userRepository.findByUsername("Kamilek")).thenReturn(Optional.of(user));
                when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));
                when(userRepository.save(user)).thenReturn(user);


                userService.addRoleToUser("Kamilek", "ROLE_USER");

                userService.deleteUserRole("Kamilek", "ROLE_USER");

                assertThat(user.getRoles()).hasSize(0);
        }

        @Test
        void itShouldAddCreditCardToUser() {
                User user = User.builder()
                        .firstName("Paul")
                        .lastName("Potato")
                        .username("niceUser45")
                        .build();

                CreditCardDto creditCardDto = CreditCardDto.builder()
                        .cardNumber(7756443322118596L)
                        .build();

                CreditCard creditCard = CreditCard.builder()
                        .cardNumber(7756443322118596L)
                        .accountBalance(0L)
                        .build();


                when(userRepository.findByUsername("niceUser45")).thenReturn(Optional.of(user));
                when(creditCardRepository.save(creditCard)).thenReturn(creditCard);
                when(userRepository.save(user)).thenReturn(user);


                userService.addCreditCardToUser("niceUser45", creditCardDto);
                assertThat(user.getCreditCard().getCardNumber()).isEqualTo(creditCardDto.getCardNumber());
        }

        @Test
        void itShouldDeleteUserCreditCard() {
                User user = User.builder()
                        .firstName("John")
                        .lastName("Octavian")
                        .username("Shell89")
                        .build();

                CreditCardDto creditCardDto = CreditCardDto.builder()
                        .cardNumber(9999111122223333L)
                        .build();

                CreditCard creditCard = CreditCard.builder()
                        .cardNumber(9999111122223333L)
                        .accountBalance(0L)
                        .build();


                when(userRepository.findByUsername("Shell89")).thenReturn(Optional.of(user));
                when(creditCardRepository.save(creditCard)).thenReturn(creditCard);


                userService.addCreditCardToUser("Shell89", creditCardDto);
                userService.deleteUserCreditCard("Shell89");
                verify(creditCardRepository, times(1)).delete(user.getCreditCard());

        }

        @Test
        void itShouldThrowExistingUserException() {
                UserInDto userInDto = UserInDto.builder()
                        .username("Flick")
                        .build();

                User user = User.builder()
                        .username("Flick")
                        .build();


                when(userRepository.findByUsername("Flick")).thenReturn(Optional.of(user));


                assertThrows(ExistingEntityException.class, () -> userService.saveUser(userInDto));
        }

        @Test
        void itShouldThrowExistingRoleException() {
                Role role = Role.builder()
                        .name("ROLE_VISITOR")
                        .build();

                Role role2 = Role.builder()
                        .name("ROLE_VISITOR")
                        .build();


                when(roleRepository.findByName("ROLE_VISITOR")).thenReturn(Optional.of(role2));


                assertThrows(ExistingEntityException.class, () -> userService.saveRole(role));
        }

        @Test
        void itShouldThrowAssignedRoleException() {
                Role role = Role.builder()
                        .name("ROLE_USER")
                        .build();

                User user = User.builder()
                        .username("Zbyszek")
                        .roles(Arrays.asList(role))
                        .build();


                when(userRepository.findByUsername("Zbyszek")).thenReturn(Optional.of(user));
                when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));


                assertThrows(AssignedRoleException.class, () -> userService.addRoleToUser("Zbyszek", "ROLE_USER"));
        }

        @Test
        void itShouldReturnAllUsers() {
                User user1 = User.builder()
                        .firstName("Adrian")
                        .lastName("Puchacki")
                        .username("puchatek")
                        .password("jabsgsdgsf4")
                        .email("Legaidf7@gmail.com")
                        .phone(675842233)
                        .build();

                User user2 = User.builder()
                        .firstName("Krzysztof")
                        .lastName("Bluza")
                        .username("hdjdhus")
                        .password("nfhusg8dsh4")
                        .email("Ohyufugd@gmail.com")
                        .phone(945769043)
                        .build();

                User user3 = User.builder()
                        .firstName("Grzegorz")
                        .lastName("Kante")
                        .username("skokl")
                        .password("gtdft47HHs8hu")
                        .email("Oktesasd7@gmail.com")
                        .phone(234665789)
                        .build();

                List<User> users = new ArrayList<>();
                users.add(user1);
                users.add(user2);
                users.add(user3);


                when(userRepository.findAll()).thenReturn(users);


                Assertions.assertThat(userService.getAllUsers()).isEqualTo(UserDtoMapper.mapUserToUserDto(users));
        }

}
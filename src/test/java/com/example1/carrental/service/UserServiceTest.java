package com.example1.carrental.service;

import com.example1.carrental.domain.CreditCard;
import com.example1.carrental.domain.Role;
import com.example1.carrental.domain.User;
import com.example1.carrental.dto.CreditCardDto;
import com.example1.carrental.dto.UserSaveDto;
import com.example1.carrental.mapper.CarSaveDtoMapper;
import com.example1.carrental.mapper.CreditCardDtoMapper;
import com.example1.carrental.mapper.UserEditDtoMapper;
import com.example1.carrental.mapper.UserSaveDtoMapper;
import com.example1.carrental.repo.CreditCardRepo;
import com.example1.carrental.repo.RoleRepo;
import com.example1.carrental.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

        @Mock
        UserRepo userRepo;

        @Mock
        RoleRepo roleRepo;

        @Mock
        CreditCardRepo creditCardRepo;

        @Mock
        PasswordEncoder passwordEncoder;

        @InjectMocks
        UserService userService;


        @Test
        void itShouldSaveUser() {
                UserSaveDto userSaveDto = UserSaveDto.builder().firstName("Sebastian").lastName("Alvarez")
                        .username("sebix").password("Marbella465").email("apple@gamil.com").phone(968956584).build();

                userSaveDto.setPassword(passwordEncoder.encode(userSaveDto.getPassword()));
                User user = UserSaveDtoMapper.mapToUser(userSaveDto);

                when(userRepo.save(user)).thenReturn(user);

                userService.saveUser(userSaveDto);

                assertThat(user.getFirstName()).isEqualTo(userSaveDto.getFirstName());
                assertThat(user.getEmail()).isEqualTo(userSaveDto.getEmail());
        }

        @Test
        void itShouldCheckIfUserIsEdited() {
                User user = User.builder().id(2L).build();

                when(userRepo.findById(2L)).thenReturn(Optional.of(user));
                when(userRepo.save(user)).thenReturn(user);

                assertThat(userService.editUser(UserEditDtoMapper.mapUserToUserDto(user))).isEqualTo(user);
        }

        @Test
        void itShouldDeleteUser() {
                User user = User.builder().id(6L).build();

                doNothing().when(userRepo).deleteById(6L);

                userService.deleteUser(6L);

                verify(userRepo, times(1)).deleteById(6L);
        }

        @Test
        void itShouldSaveRole() {
                Role role = Role.builder().name("ROLE_MANAGER").build();

                when(roleRepo.save(role)).thenReturn(role);

                Role saved = userService.saveRole(role);

                assertThat(saved).isEqualTo(role);
        }

        @Test
        void itShouldAddRoleToUser() {
                User user = User.builder().firstName("Pawel").lastName("Mroczek").username("pablo").roles(new ArrayList<>()).build();
                Role role = Role.builder().name("ROLE_ADMIN").build();

                when(userRepo.findByUsername("pablo")).thenReturn(Optional.of(user));
                when(roleRepo.findByName("ROLE_ADMIN")).thenReturn(Optional.of(role));
                when(userRepo.save(user)).thenReturn(user);

                userService.addRoleToUser("pablo", "ROLE_ADMIN");

                assertThat(user.getRoles()).hasSize(1);
        }

        @Test
        void itShouldDeleteUserRole() {
                User user = User.builder().firstName("Kamil").lastName("Jasny").username("Kamilek").roles(new ArrayList<>()).build();
                Role role = Role.builder().name("ROLE_USER").build();

                when(userRepo.findByUsername("Kamilek")).thenReturn(Optional.of(user));
                when(roleRepo.findByName("ROLE_USER")).thenReturn(Optional.of(role));
                when(userRepo.save(user)).thenReturn(user);

                userService.addRoleToUser("Kamilek", "ROLE_USER");

                userService.deleteUserRole("Kamilek", "ROLE_USER");

                assertThat(user.getRoles()).hasSize(0);
        }

        @Test
        void itShouldAddCreditCardToUser() {
                User user = User.builder().firstName("Paul").lastName("Potato").username("niceUser45").build();
                CreditCardDto creditCardDto = CreditCardDto.builder().cardNumber(7756443322118596L).build();

                when(userRepo.findByUsername("niceUser45")).thenReturn(Optional.of(user));

                try (MockedStatic<CreditCardDtoMapper> mockedStatic = Mockito.mockStatic(CreditCardDtoMapper.class)) {

                        CreditCard creditCard = CreditCard.builder().cardNumber(7756443322118596L).build();

                        when(CreditCardDtoMapper.mapToCreditCard(creditCardDto)).thenReturn(creditCard);
                        when(creditCardRepo.save(creditCard)).thenReturn(creditCard);
                        when(userRepo.save(user)).thenReturn(user);

                        userService.addCreditCardToUser("niceUser45", creditCardDto);

                        assertThat(user.getCreditCard().getCardNumber()).isEqualTo(creditCardDto.getCardNumber());
                }
        }

        @Test
        void itShouldDeleteUserCreditCard() {
                User user = User.builder().firstName("John").lastName("Octavian").username("Shell89").build();
                CreditCardDto creditCardDto = CreditCardDto.builder().cardNumber(9999111122223333L).build();

                when(userRepo.findByUsername("Shell89")).thenReturn(Optional.of(user));

                try (MockedStatic<CreditCardDtoMapper> mockedStatic = Mockito.mockStatic(CreditCardDtoMapper.class)) {

                        CreditCard creditCard = CreditCard.builder().cardNumber(9999111122223333L).build();

                        when(CreditCardDtoMapper.mapToCreditCard(creditCardDto)).thenReturn(creditCard);
                        when(creditCardRepo.save(creditCard)).thenReturn(creditCard);


                        userService.addCreditCardToUser("Shell89", creditCardDto);

                        userService.deleteUserCreditCard("Shell89");

                        verify(creditCardRepo, times(1)).delete(user.getCreditCard());
                }
        }

        @Test
        void itShouldReturnAllUsers() {
                User user1 = User.builder().firstName("Adrian").lastName("Puchacki").username("puchatek").password("jabsgsdgsf4").email("Legaidf7@gmail.com").phone(675842233).build();
                User user2 = User.builder().firstName("Krzysztof").lastName("Bluza").username("hdjdhus").password("nfhusg8dsh4").email("Ohyufugd@gmail.com").phone(945769043).build();
                User user3 = User.builder().firstName("Grzegorz").lastName("Kante").username("skokl").password("gtdft47HHs8hu").email("Oktesasd7@gmail.com").phone(234665789).build();
                List<User> users = new ArrayList<>();
                users.add(user1);
                users.add(user2);
                users.add(user3);

                when(userRepo.findAll()).thenReturn(users);

                assertThat(userService.getUsers()).isEqualTo(users);
        }

}
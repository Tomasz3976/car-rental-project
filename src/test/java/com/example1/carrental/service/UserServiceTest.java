package com.example1.carrental.service;

import com.example1.carrental.domain.Role;
import com.example1.carrental.domain.User;
import com.example1.carrental.dto.UserSaveDto;
import com.example1.carrental.mapper.UserEditDtoMapper;
import com.example1.carrental.mapper.UserSaveDtoMapper;
import com.example1.carrental.repo.RoleRepo;
import com.example1.carrental.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
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
        PasswordEncoder passwordEncoder;

        @InjectMocks
        UserService userService;


        @Test
        void itShouldSaveUser() {
                UserSaveDto userSaveDto = new UserSaveDto( "Sebastian", "Alvarez", "sebix", "Marbella465", "apple@gamil.com", 968956584);
                userSaveDto.setPassword(passwordEncoder.encode(userSaveDto.getPassword()));
                User user = UserSaveDtoMapper.mapToUser(userSaveDto);

                when(userRepo.save(user)).thenReturn(user);

                userService.saveUser(userSaveDto);

                assertThat(user.getFirstName()).isEqualTo(userSaveDto.getFirstName());
                assertThat(user.getEmail()).isEqualTo(userSaveDto.getEmail());
        }

        @Test
        void itShouldCheckIfUserIsEdited() {
                User user = new User(2L, "John", "Sudo", "jan", "Jahsksh473", "email@email.com", 748392394, new ArrayList<>());

                when(userRepo.findById(2L)).thenReturn(Optional.of(user));
                when(userRepo.save(user)).thenReturn(user);

                assertThat(userService.editUser(UserEditDtoMapper.mapUserToUserDto(user))).isEqualTo(user);
        }

        @Test
        void itShouldDeleteUser() {
                User user = new User(6L, "Jan", "Kaczmarczyk", "janeko", "kdjdjdff364Hd", "Duck123@gmail.com", 506958432, new ArrayList<>());

                doNothing().when(userRepo).deleteById(6L);

                userService.deleteUser(6L);

                verify(userRepo, times(1)).deleteById(6L);
        }

        @Test
        void itShouldSaveRole() {
                Role role = new Role(null, "ROLE_USER");

                when(roleRepo.save(role)).thenReturn(role);

                Role saved = userService.saveRole(role);

                assertThat(saved).isEqualTo(role);
        }

        @Test
        void itShouldAddRoleToUser() {
                User user = new User(3L, "Pawel", "Mroczek", "mroczko", "jablomnj4744", "Earndga84@gmail.com", 354284233, new ArrayList<>());
                Role role = new Role(null, "ROLE_ADMIN");

                when(userRepo.findByUsername("mroczko")).thenReturn(Optional.of(user));
                when(roleRepo.findByName("ROLE_ADMIN")).thenReturn(Optional.of(role));
                when(userRepo.save(user)).thenReturn(user);

                userService.addRoleToUser("mroczko", "ROLE_ADMIN");

                assertThat(user.getRoles()).hasSize(1);
        }

        @Test
        void itShouldDeleteUserRole() {
                User user = new User(3L, "Kamil", "Jasny", "Kamilek", "jablsfdfasf4", "Operty47@gmail.com", 332422233, new ArrayList<>());
                Role role = new Role(null, "ROLE_USER");

                when(userRepo.findByUsername("Kamilek")).thenReturn(Optional.of(user));
                when(roleRepo.findByName("ROLE_USER")).thenReturn(Optional.of(role));
                when(userRepo.save(user)).thenReturn(user);

                userService.addRoleToUser("Kamilek", "ROLE_USER");

                userService.deleteUserRole("Kamilek", "ROLE_USER");

                assertThat(user.getRoles()).hasSize(0);
        }

        @Test
        void itShouldReturnAllUsers() {
                User user1 = new User(null, "Adrian", "Puchacki", "puchatek", "jabsgsdgsf4", "Legaidf7@gmail.com", 675842233, new ArrayList<>());
                User user2 = new User(null, "Krzysztof", "Bluza", "hdjdhus", "nfhusg8dsh4", "Ohyufugd@gmail.com", 945769043, new ArrayList<>());
                User user3 = new User(null, "Grzegorz", "Kante", "skokl", "gtdft47HHs8hu", "Oktesasd7@gmail.com", 234665789, new ArrayList<>());
                ArrayList<User> users = new ArrayList<>();
                users.add(user1);
                users.add(user2);
                users.add(user3);

                when(userRepo.findAll()).thenReturn(users);

                assertThat(userService.getUsers()).isEqualTo(users);
        }

}
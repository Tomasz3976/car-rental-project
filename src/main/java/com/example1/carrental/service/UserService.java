package com.example1.carrental.service;

import com.example1.carrental.domain.CreditCard;
import com.example1.carrental.domain.Role;
import com.example1.carrental.domain.User;
import com.example1.carrental.dto.CreditCardDto;
import com.example1.carrental.dto.UserDto;
import com.example1.carrental.exception.AssignedRoleException;
import com.example1.carrental.exception.ExistingEntityException;
import com.example1.carrental.mapper.UserDtoMapper;
import com.example1.carrental.repo.CreditCardRepo;
import com.example1.carrental.repo.RoleRepo;
import com.example1.carrental.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.example1.carrental.mapper.CreditCardDtoMapper.mapToCreditCard;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

        private final UserRepo userRepo;
        private final RoleRepo roleRepo;
        private final CreditCardRepo creditCardRepo;
        private final PasswordEncoder passwordEncoder;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepo.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Username Not Found!"));

                log.info("User found in the database: {}", username);

                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                user.getRoles().forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role.getName()));
                });
                return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }

        public UserDto saveUser(UserDto userDto) {
                if(userRepo.findByUsername(userDto.getUsername()).isPresent()) {

                        throw new ExistingEntityException("User With Given Username Already Exists!");
                }
                log.info("Saving new user {} to the database", userDto.getUsername());
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
                userRepo.save(UserDtoMapper.mapToUser(userDto));
                return userDto;
        }

        public User editUser(Long id, UserDto userDto) {
                User userEdited = userRepo.findById(id)
                        .orElseThrow(() -> new UsernameNotFoundException("This User Does Not Exists!"));
                log.info("Edition user with id {}", id);
                userEdited.setFirstName(userDto.getFirstName());
                userEdited.setLastName(userDto.getLastName());
                userEdited.setUsername(userDto.getUsername());
                userEdited.setPassword(passwordEncoder.encode(userDto.getPassword()));
                userEdited.setEmail(userDto.getEmail());
                userEdited.setPhone(userDto.getPhone());
                return userRepo.save(userEdited);
        }

        public void deleteUser(Long id) {
                log.info("Deleting user with id {}", id);
                userRepo.deleteById(id);
        }

        public Role saveRole(Role role) {
                log.info("Saving new role {} to the database", role.getName());
                if(roleRepo.findByName(role.getName()).isPresent()) {

                        throw new ExistingEntityException("Role With Given Name Already Exists!");
                }
                return roleRepo.save(role);
        }

        public User addRoleToUser(String username, String roleName) {
                log.info("Adding role {} to user {}", roleName, username);
                User user = userRepo.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("This User Does Not Exists!"));
                Role role = roleRepo.findByName(roleName)
                        .orElseThrow(() -> new EntityNotFoundException("This Role Does Not Exists!"));
                if(user.getRoles().contains(role)) {

                        throw new AssignedRoleException("User Already Has This Role");
                }
                user.getRoles().add(role);
                role.getUsers().add(user);
                return userRepo.save(user);
        }

        public void deleteUserRole(String username, String roleName) {
                log.info("Deleting role {} of user {}", roleName, username);
                User user = userRepo.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("This User Does Not Exists!"));
                Role role = roleRepo.findByName(roleName)
                        .orElseThrow(() -> new EntityNotFoundException("This Role Does Not Exists!"));
                user.getRoles().remove(role);
                role.getUsers().remove(user);
        }

        public User addCreditCardToUser(String username, CreditCardDto creditCardDto) {
                log.info("Adding credit card to user {}", username);
                User user = userRepo.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("This User Does Not Exists!"));

                if(user.getCreditCard() != null) {
                        throw new IllegalCallerException("User Already Has Credit Card!");
                }
                CreditCard card = creditCardRepo.save(mapToCreditCard(creditCardDto));
                user.setCreditCard(card);
                card.setUser(user);
                return userRepo.save(user);
        }

        public void deleteUserCreditCard(String username) {
                log.info("Deleting credit card of user {}", username);
                User user = userRepo.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("This User Does Not Exists!"));
                creditCardRepo.delete(user.getCreditCard());
        }

        public List<User> getUsers() {
                log.info("Fetching all users");
                return userRepo.findAll();
        }

}

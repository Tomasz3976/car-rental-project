package com.example1.carrental.service;

import com.example1.carrental.domain.CreditCard;
import com.example1.carrental.domain.Role;
import com.example1.carrental.domain.User;
import com.example1.carrental.dto.UserEditDto;
import com.example1.carrental.dto.UserSaveDto;
import com.example1.carrental.mapper.UserSaveDtoMapper;
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

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

        private final UserRepo userRepo;
        private final RoleRepo roleRepo;
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

        public UserSaveDto saveUser(UserSaveDto userSaveDto) {
                log.info("Saving new user {} to the database", userSaveDto.getUsername());
                userSaveDto.setPassword(passwordEncoder.encode(userSaveDto.getPassword()));
                userRepo.save(UserSaveDtoMapper.mapToUser(userSaveDto));
                return userSaveDto;
        }

        public User editUser(UserEditDto userEditDto) {
                User userEdited = userRepo.findById(userEditDto.getId())
                        .orElseThrow(() -> new UsernameNotFoundException("This User Does Not Exists!"));
                log.info("Edition user with id {}", userEditDto.getId());
                userEdited.setFirstName(userEditDto.getFirstName());
                userEdited.setLastName(userEditDto.getLastName());
                userEdited.setUsername(userEditDto.getUsername());
                userEdited.setPassword(passwordEncoder.encode(userEditDto.getPassword()));
                userEdited.setEmail(userEditDto.getEmail());
                userEdited.setPhone(userEditDto.getPhone());
                return userRepo.save(userEdited);
        }

        public void deleteUser(Long id) {
                log.info("Deleting user with id {}", id);
                userRepo.deleteById(id);
        }

        public Role saveRole(Role role) {
                log.info("Saving new role {} to the database", role.getName());
                return roleRepo.save(role);
        }

        public User addRoleToUser(String username, String roleName) {
                log.info("Adding role {} to user {}", roleName, username);
                User user = userRepo.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("This User Does Not Exists!"));
                Role role = roleRepo.findByName(roleName)
                        .orElseThrow(() -> new EntityNotFoundException("This Role Does Not Exists!"));
                user.getRoles().add(role);
                return userRepo.save(user);
        }

        public void deleteUserRole(String username, String roleName) {
                log.info("Deleting role {} of user {}", roleName, username);
                User user = userRepo.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("This User Does Not Exists!"));
                Role role = roleRepo.findByName(roleName)
                        .orElseThrow(() -> new EntityNotFoundException("This Role Does Not Exists!"));
                user.getRoles().remove(role);
        }

        public User addCreditCardToUser(String username, CreditCard creditCard) {
                log.info("Adding credit card to user {}", username);
                User user = userRepo.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("This User Does Not Exists!"));
                user.setCreditCard(creditCard);
                return userRepo.save(user);
        }

        public void deleteUserCreditCard(String username) {
                log.info("Deleting credit card of user {}", username);
                User user = userRepo.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("This User Does Not Exists!"));
                user.setCreditCard(null);
        }

        public List<User> getUsers() {
                log.info("Fetching all users");
                return userRepo.findAll();
        }

}

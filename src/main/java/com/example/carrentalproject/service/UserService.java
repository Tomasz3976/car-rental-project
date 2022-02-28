package com.example.carrentalproject.service;

import com.example.carrentalproject.domain.CreditCard;
import com.example.carrentalproject.domain.Role;
import com.example.carrentalproject.domain.User;
import com.example.carrentalproject.dto.CreditCardDto;
import com.example.carrentalproject.dto.UserDto;
import com.example.carrentalproject.mapper.UserDtoMapper;
import com.example.carrentalproject.mapper.UserInDtoMapper;
import com.example.carrentalproject.repository.CreditCardRepository;
import com.example.carrentalproject.repository.RoleRepository;
import com.example.carrentalproject.dto.UserInDto;
import com.example.carrentalproject.exception.AssignedRoleException;
import com.example.carrentalproject.exception.ExistingEntityException;
import com.example.carrentalproject.exception.NoCreditCardException;
import com.example.carrentalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.example.carrentalproject.mapper.CreditCardDtoMapper.mapToCreditCard;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final CreditCardRepository creditCardRepository;
        private final PasswordEncoder passwordEncoder;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Username Not Found!"));

                log.info("User found in the database: {}", username);

                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                user.getRoles().forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role.getName()));
                });
                return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }

        public UserInDto saveUser(UserInDto userInDto) {
                if(userRepository.findByUsername(userInDto.getUsername()).isPresent()) {

                        throw new ExistingEntityException("User With Given Username Already Exists!");
                }
                log.info("Saving new user {} to the database", userInDto.getUsername());
                userInDto.setPassword(passwordEncoder.encode(userInDto.getPassword()));
                userRepository.save(UserInDtoMapper.mapToUser(userInDto));
                return userInDto;
        }

        public User editUser(Long id, UserInDto userInDto) {
                User userEdited = userRepository.findById(id)
                        .orElseThrow(() -> new UsernameNotFoundException("This User Does Not Exists!"));
                log.info("Edition user with id {}", id);
                userEdited.setFirstName(userInDto.getFirstName());
                userEdited.setLastName(userInDto.getLastName());
                userEdited.setUsername(userInDto.getUsername());
                userEdited.setPassword(passwordEncoder.encode(userInDto.getPassword()));
                userEdited.setEmail(userInDto.getEmail());
                userEdited.setPhone(userInDto.getPhone());
                return userRepository.save(userEdited);
        }

        public void deleteUser(Long id) {
                log.info("Deleting user with id {}", id);
                if(!userRepository.existsById(id)) {

                        throw new UsernameNotFoundException("This User Does Not Exists!");
                }
                userRepository.deleteById(id);
        }

        public Role saveRole(Role role) {
                log.info("Saving new role {} to the database", role.getName());
                if(roleRepository.findByName(role.getName()).isPresent()) {

                        throw new ExistingEntityException("Role With Given Name Already Exists!");
                }
                return roleRepository.save(role);
        }

        public User addRoleToUser(String username, String roleName) {
                log.info("Adding role {} to user {}", roleName, username);
                User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("This User Does Not Exists!"));
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new EntityNotFoundException("This Role Does Not Exists!"));
                if(user.getRoles().contains(role)) {

                        throw new AssignedRoleException("User Already Has This Role");
                }
                user.getRoles().add(role);
                role.getUsers().add(user);
                return userRepository.save(user);
        }

        public void deleteUserRole(String username, String roleName) {
                log.info("Deleting role {} of user {}", roleName, username);
                User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("This User Does Not Exists!"));
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new EntityNotFoundException("This Role Does Not Exists!"));
                user.getRoles().remove(role);
                role.getUsers().remove(user);
        }

        public User addCreditCardToUser(String username, CreditCardDto creditCardDto) {
                log.info("Adding credit card to user {}", username);
                User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("This User Does Not Exists!"));

                if(user.getCreditCard() != null) {

                        throw new IllegalCallerException("User Already Has Credit Card!");
                }
                CreditCard card = creditCardRepository.save(mapToCreditCard(creditCardDto));
                user.setCreditCard(card);
                card.setUser(user);
                return userRepository.save(user);
        }

        public void deleteUserCreditCard(String username) {
                log.info("Deleting credit card of user {}", username);
                User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("This User Does Not Exists!"));
                if(user.getCreditCard() == null) {

                        throw new NoCreditCardException("This User Do Not Have Credit Card!");
                }
                creditCardRepository.delete(user.getCreditCard());
        }

        public List<UserDto> getAllUsers() {
                log.info("Fetching all users");
                return UserDtoMapper.mapUserToUserDto(userRepository.findAll());
        }

}

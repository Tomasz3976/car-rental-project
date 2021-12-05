package com.example1.carrental.service;

import com.example1.carrental.domain.CreditCard;
import com.example1.carrental.domain.User;
import com.example1.carrental.dto.CreditCardDto;
import com.example1.carrental.dto.UserSaveDto;
import com.example1.carrental.mapper.UserSaveDtoMapper;
import com.example1.carrental.repo.UserRepo;
import com.example1.carrental.security.LoggedInUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example1.carrental.mapper.CreditCardDtoMapper.mapToCreditCard;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RegistrationService {

        private final UserRepo userRepo;
        private final PasswordEncoder passwordEncoder;
        private final UserService userService;
        private final LoggedInUser loggedInUser;


        public void registerUser(UserSaveDto userSaveDto) {

                if (userRepo.findByUsername(userSaveDto.getUsername()).isPresent()) {

                        log.info("User with the given name already exists!");

                } else if (!PasswordValidator.matcher(userSaveDto.getPassword()).matches()) {

                        log.info("Password must contains minimum eight characters," +
                                " at least one uppercase letter, one lowercase letter and one number!");
                } else {

                        log.info("Registration of new user");
                        User user = UserSaveDtoMapper.mapToUser(userSaveDto);
                        user.setPassword(passwordEncoder.encode(user.getPassword()));
                        userRepo.save(user);
                        userService.addRoleToUser(user.getUsername(), "ROLE_USER");

                }
        }

        public void addCreditCard(CreditCardDto creditCardDto) {

                log.info("Adding credit card to user");
                User user = loggedInUser.getUser();
                CreditCard creditCard = mapToCreditCard(creditCardDto);
                user.setCreditCard(creditCard);
                userRepo.save(user);
        }

        public void moneyTransfer(Long moneyAmount) {

                User user = loggedInUser.getUser();

                if(user.getCreditCard() == null) {

                        log.info("First you need to add a credit card!");

                } else {

                        log.info("Transfer for the amount of {}", moneyAmount);
                        CreditCard creditCard = user.getCreditCard();
                        creditCard.setAccountBalance(creditCard.getAccountBalance() + moneyAmount);
                        userRepo.save(user);

                }
        }

}

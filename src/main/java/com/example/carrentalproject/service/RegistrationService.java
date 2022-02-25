package com.example.carrentalproject.service;

import com.example.carrentalproject.domain.CreditCard;
import com.example.carrentalproject.domain.User;
import com.example.carrentalproject.dto.CreditCardDto;
import com.example.carrentalproject.dto.UserDto;
import com.example.carrentalproject.mapper.UserDtoMapper;
import com.example.carrentalproject.repo.CreditCardRepository;
import com.example.carrentalproject.security.LoggedInUser;
import com.example.carrentalproject.exception.ExistingEntityException;
import com.example.carrentalproject.exception.NoCreditCardException;
import com.example.carrentalproject.exception.WeakPasswordException;
import com.example.carrentalproject.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.carrentalproject.mapper.CreditCardDtoMapper.mapToCreditCard;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RegistrationService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final UserService userService;
        private final CreditCardRepository creditCardRepository;
        private final LoggedInUser loggedInUser;


        public void registerUser(UserDto userDto) {

                if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {

                        throw new ExistingEntityException("User With Given Username Already Exists!");

                } else if (!PasswordValidator.matcher(userDto.getPassword()).matches()) {

                        throw new WeakPasswordException("Password Must Contains Minimum Eight Characters," +
                                " At Least One Uppercase Letter, One Lowercase Letter And One Number!");

                } else {

                        log.info("Registration of new user");
                        User user = UserDtoMapper.mapToUser(userDto);
                        user.setPassword(passwordEncoder.encode(user.getPassword()));
                        userRepository.save(user);
                        userService.addRoleToUser(user.getUsername(), "ROLE_USER");

                }
        }

        public void addCreditCard(CreditCardDto creditCardDto) {

                log.info("Adding credit card to user");
                User user = loggedInUser.getUser();

                if(user.getCreditCard() != null) throw new IllegalCallerException("You Already Have Credit Card!");
                CreditCard card = creditCardRepository.save(mapToCreditCard(creditCardDto));
                user.setCreditCard(card);
                card.setUser(user);
                userRepository.save(user);
        }

        public void moneyTransfer(Long moneyAmount) {

                User user = loggedInUser.getUser();

                if(user.getCreditCard() == null) {

                        throw new NoCreditCardException("You Do Not Have Credit Card!");

                } else {

                        log.info("Transfer for the amount of {}", moneyAmount);
                        CreditCard creditCard = user.getCreditCard();
                        creditCard.setAccountBalance(creditCard.getAccountBalance() + moneyAmount);
                        userRepository.save(user);

                }
        }

}

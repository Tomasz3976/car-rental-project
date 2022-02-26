package com.example.carrentalproject.service;

import com.example.carrentalproject.domain.CreditCard;
import com.example.carrentalproject.domain.User;
import com.example.carrentalproject.dto.CreditCardDto;
import com.example.carrentalproject.exception.NoCreditCardException;
import com.example.carrentalproject.repository.CreditCardRepository;
import com.example.carrentalproject.repository.UserRepository;
import com.example.carrentalproject.security.LoggedInUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.carrentalproject.mapper.CreditCardDtoMapper.mapToCreditCard;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaymentService {

        private final UserRepository userRepository;
        private final CreditCardRepository creditCardRepository;
        private final LoggedInUser loggedInUser;

        public void addCreditCard(CreditCardDto creditCardDto) {

                log.info("Adding credit card to user");
                User user = loggedInUser.getUser();

                if(user.getCreditCard() != null) {

                        throw new IllegalCallerException("You Already Have Credit Card!");
                }
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

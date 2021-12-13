package com.example1.carrental.service;

import com.example1.carrental.domain.AccessKey;
import com.example1.carrental.domain.CarPackage;
import com.example1.carrental.domain.User;
import com.example1.carrental.exception.InsufficientFundsException;
import com.example1.carrental.exception.NoCreditCardException;
import com.example1.carrental.repo.CarPackageRepo;
import com.example1.carrental.security.LoggedInUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

        private final Long ID = null;
        private final CarPackageRepo carPackageRepo;
        private final LoggedInUser loggedInUser;

        public AccessKey submitOrder(String carPackage, Integer hours) {

                User user = loggedInUser.getUser();

                if(user.getCreditCard() == null) {

                        throw new NoCreditCardException("You Do Not Have Credit Card!");

                }

                Long money = user.getCreditCard().getAccountBalance();
                CarPackage carPackageSearch = carPackageRepo.findByPackageName(carPackage)
                        .orElseThrow(() -> new EntityNotFoundException("This Package Does Not Exists!"));
                Integer price = carPackageSearch.getPricePerHour();

                AccessKey accessKey;

                if (money < (long) price * hours) {

                        throw new InsufficientFundsException("You do not have enough money!");

                } else {

                        user.getCreditCard().setAccountBalance(money - (long) price * hours);
                        accessKey = new AccessKey(ID, carPackage, hours);
                        user.setAccessKey(accessKey);

                        log.info("You managed to rent a car!");

                }
                return accessKey;
        }

}

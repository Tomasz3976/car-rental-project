package com.example1.carrental.service;

import com.example1.carrental.domain.AccessKey;
import com.example1.carrental.domain.CarPackage;
import com.example1.carrental.domain.User;
import com.example1.carrental.dto.AccessKeyDto;
import com.example1.carrental.exception.ExistingOrderException;
import com.example1.carrental.exception.InsufficientFundsException;
import com.example1.carrental.exception.NoCreditCardException;
import com.example1.carrental.repo.AccessKeyRepo;
import com.example1.carrental.repo.CarPackageRepo;
import com.example1.carrental.security.LoggedInUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static com.example1.carrental.mapper.AccessKeyDtoMapper.mapToAccessKeyDto;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

        private final Long ID = null;
        private final CarPackageRepo carPackageRepo;
        private final AccessKeyRepo accessKeyRepo;
        private final LoggedInUser loggedInUser;

        public AccessKeyDto submitOrder(String carPackage, Integer hours) {

                User user = loggedInUser.getUser();

                if(user.getCreditCard() == null) throw new NoCreditCardException("You Do Not Have Credit Card!");

                if(user.getAccessKey() != null) throw new ExistingOrderException("You Have Already Placed An Order!");

                Long money = user.getCreditCard().getAccountBalance();
                CarPackage carPackageSearch = carPackageRepo.findByPackageName(carPackage)
                        .orElseThrow(() -> new EntityNotFoundException("This Package Does Not Exists!"));
                Integer price = carPackageSearch.getPricePerHour();

                AccessKey accessKey;

                if (money < (long) price * hours) {

                        throw new InsufficientFundsException("You Do Not Have Enough Money!");

                } else {

                        user.getCreditCard().setAccountBalance(money - (long) price * hours);
                        accessKey = new AccessKey(ID, carPackage, hours, null);
                        accessKeyRepo.save(accessKey);
                        user.setAccessKey(accessKey);
                        accessKey.setUser(user);

                        log.info("You managed to rent a car!");

                }
                AccessKeyDto accessKeyDto = mapToAccessKeyDto(accessKey);
                return accessKeyDto;
        }

}

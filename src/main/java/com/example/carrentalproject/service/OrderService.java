package com.example.carrentalproject.service;

import com.example.carrentalproject.domain.AccessKey;
import com.example.carrentalproject.domain.CarPackage;
import com.example.carrentalproject.domain.PlacedOrder;
import com.example.carrentalproject.domain.User;
import com.example.carrentalproject.dto.AccessKeyDto;
import com.example.carrentalproject.exception.ExistingOrderException;
import com.example.carrentalproject.repository.CarPackageRepository;
import com.example.carrentalproject.repository.OrderRepository;
import com.example.carrentalproject.security.LoggedInUser;
import com.example.carrentalproject.exception.InsufficientFundsException;
import com.example.carrentalproject.exception.NoCreditCardException;
import com.example.carrentalproject.repository.AccessKeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.List;

import static com.example.carrentalproject.mapper.AccessKeyDtoMapper.mapToAccessKeyDto;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

        private final Long ID = null;
        private final CarPackageRepository carPackageRepository;
        private final OrderRepository orderRepository;
        private final AccessKeyRepository accessKeyRepository;
        private final LoggedInUser loggedInUser;

        public List<PlacedOrder> getOrders() {
                log.info("Fetching all orders");
                return orderRepository.findAll();
        }

        public AccessKeyDto submitOrder(String carPackage, Integer hours) {

                User user = loggedInUser.getUser();

                if(user.getCreditCard() == null) {

                        throw new NoCreditCardException("You Do Not Have Credit Card!");
                }
                if(user.getAccessKey() != null) {

                        throw new ExistingOrderException("You Have Already Placed An Order!");
                }
                Long money = user.getCreditCard().getAccountBalance();
                CarPackage carPackageSearch = carPackageRepository.findByPackageName(carPackage)
                        .orElseThrow(() -> new EntityNotFoundException("This Package Does Not Exists!"));
                Integer price = carPackageSearch.getPricePerHour();

                AccessKey accessKey;

                if (money < (long) price * hours) {

                        throw new InsufficientFundsException("You Do Not Have Enough Money!");
                } else {

                        user.getCreditCard().setAccountBalance(money - (long) price * hours);
                        accessKey = new AccessKey(ID, carPackage, hours, null);
                        accessKeyRepository.save(accessKey);
                        user.setAccessKey(accessKey);
                        accessKey.setUser(user);

                        log.info("You managed to rent a car!");

                }
                AccessKeyDto accessKeyDto = mapToAccessKeyDto(accessKey);
                return accessKeyDto;
        }

}

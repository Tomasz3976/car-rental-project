package com.example.carrentalproject.service;

import com.example.carrentalproject.domain.Car;
import com.example.carrentalproject.domain.PlacedOrder;
import com.example.carrentalproject.domain.User;
import com.example.carrentalproject.exception.UnavailableCarException;
import com.example.carrentalproject.repository.CarRepository;
import com.example.carrentalproject.repository.OrderRepository;
import com.example.carrentalproject.security.LoggedInUser;
import com.example.carrentalproject.exception.InvalidPackageException;
import com.example.carrentalproject.exception.NoAccessKeyException;
import com.example.carrentalproject.repository.AccessKeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DeliveryService {

        public static final Long ID = null;
        private final CarRepository carRepository;
        private final OrderRepository orderRepository;
        private final AccessKeyRepository accessKeyRepository;
        private final LoggedInUser loggedInUser;

        public Car pickUpTheCar(Long carId) {

                Car car = carRepository.findById(carId)
                        .orElseThrow(() -> new EntityNotFoundException("Car With This ID Does Not Exists!"));
                User user = loggedInUser.getUser();
                if(user.getAccessKey() == null) {

                        throw new NoAccessKeyException("You Do Not Have An Access Key!");
                }
                else if(!user.getAccessKey().getCarPackage().equals(car.getCarPackage().getPackageName())) {

                        throw new InvalidPackageException("You Cannot Pick Car From This Package!");
                }
                else if(!car.getIsAvailable()) {

                        throw new UnavailableCarException("This Car Is Not Available!");
                } else {

                        accessKeyRepository.delete(user.getAccessKey());
                        car.setIsAvailable(false);
                        LocalDateTime start = LocalDateTime.now();
                        LocalDateTime end = LocalDateTime.now().plusHours(user.getAccessKey().getHours());
                        PlacedOrder order = new PlacedOrder(ID, user.getId(), car.getId(), car.getBrand(), car.getModel(), start, end);
                        orderRepository.save(order);

                        log.info("You rented a car, have a nice trip!");
                }
                return car;
        }

}

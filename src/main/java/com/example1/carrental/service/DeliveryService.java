package com.example1.carrental.service;

import com.example1.carrental.domain.Car;
import com.example1.carrental.domain.PlacedOrder;
import com.example1.carrental.domain.User;
import com.example1.carrental.exception.InvalidPackageException;
import com.example1.carrental.exception.NoAccessKeyException;
import com.example1.carrental.exception.UnavailableCarException;
import com.example1.carrental.repo.AccessKeyRepo;
import com.example1.carrental.repo.CarRepo;
import com.example1.carrental.repo.OrderRepo;
import com.example1.carrental.security.LoggedInUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DeliveryService {

        public static final Long ID = null;
        private final CarRepo carRepo;
        private final OrderRepo orderRepo;
        private final AccessKeyRepo accessKeyRepo;
        private final LoggedInUser loggedInUser;


        public Car pickUpTheCar(Long carId) {

                Car car = carRepo.findById(carId)
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


                        accessKeyRepo.delete(user.getAccessKey());
                        car.setIsAvailable(false);
                        LocalDateTime start = LocalDateTime.now();
                        LocalDateTime end = LocalDateTime.now().plusHours(user.getAccessKey().getHours());
                        PlacedOrder order = new PlacedOrder(ID, user.getId(), car.getId(), car.getBrand(), car.getModel(), start, end);
                        orderRepo.save(order);

                        log.info("You rented a car, have a nice trip!");

                }
                return car;
        }

}

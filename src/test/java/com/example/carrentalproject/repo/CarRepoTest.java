package com.example.carrentalproject.repo;

import com.example.carrentalproject.constant.FuelType;
import com.example.carrentalproject.constant.GearBoxType;
import com.example.carrentalproject.domain.Car;
import com.example.carrentalproject.domain.CarPackage;
import com.example.carrentalproject.domain.CarParameters;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarRepoTest {

        @Autowired
        private CarRepo carRepo;

        @Autowired
        private CarPackageRepo carPackageRepo;

        @BeforeEach
        void setUp() {
                CarPackage sporty = new CarPackage(null, "Sporty", 300, new ArrayList<>());

                carPackageRepo.save(sporty);

                Car car = new Car(null, "RSA45362", "Audi", "S6", true, sporty,
                        new CarParameters(null, FuelType.PETROL, GearBoxType.AUTOMATIC, 5, 5, true));
                        sporty.getCars().add(car);
                carRepo.save(car);
        }

        @AfterEach
        void tearDown() {
                carRepo.deleteAll();
        }

        @Test
        void itShouldReturnAvailableCars() {

                List<Car> cars = carRepo.findCars(PageRequest.of(1, 20, Sort.by(Sort.Direction.ASC, "id")));

                assertThat(carRepo.findAvailableCars(PageRequest.of(1, 20, Sort.by(Sort.Direction.ASC, "id"))))
                        .isEqualTo(cars.stream()
                                .filter(car -> car.getIsAvailable().equals(true))
                                .collect(Collectors.toList()));

        }

}
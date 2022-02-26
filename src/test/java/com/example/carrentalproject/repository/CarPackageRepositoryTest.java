package com.example.carrentalproject.repository;

import com.example.carrentalproject.domain.CarPackage;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarPackageRepositoryTest {

        @Autowired
        private CarPackageRepository carPackageRepository;

        @BeforeEach
        void setUp() {
                CarPackage sporty = new CarPackage(null, "Sporty", 300, new ArrayList<>());
                CarPackage luxury = new CarPackage(null, "Luxury", 500, new ArrayList<>());
                CarPackage ordinary = new CarPackage(null, "Ordinary", 100, new ArrayList<>());
                carPackageRepository.save(sporty);
                carPackageRepository.save(luxury);
                carPackageRepository.save(ordinary);
        }

        @AfterEach
        void tearDown() {
                carPackageRepository.deleteAll();
        }

        @Test
        void itShouldFindPackageByName() {
                AssertionsForClassTypes.assertThat(carPackageRepository.findByPackageName("Sporty")).isInstanceOf(Optional.class).isPresent();
                AssertionsForClassTypes.assertThat(carPackageRepository.findByPackageName("Luxury")).isInstanceOf(Optional.class).isPresent();
                AssertionsForClassTypes.assertThat(carPackageRepository.findByPackageName("Retro")).isInstanceOf(Optional.class).isEmpty();
                AssertionsForClassTypes.assertThat(carPackageRepository.findByPackageName("Ordinary")).isInstanceOf(Optional.class).isPresent();
                AssertionsForClassTypes.assertThat(carPackageRepository.findByPackageName("HyperCar")).isInstanceOf(Optional.class).isEmpty();
                AssertionsForClassTypes.assertThat(carPackageRepository.findByPackageName("Eco")).isInstanceOf(Optional.class).isEmpty();
        }

}
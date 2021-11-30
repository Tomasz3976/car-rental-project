package com.example1.carrental.service;

import com.example1.carrental.domain.Car;
import com.example1.carrental.domain.CarPackage;
import com.example1.carrental.repo.CarPackageRepo;
import com.example1.carrental.repo.CarRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CarService {

        private final CarRepo carRepo;
        private final CarPackageRepo carPackageRepo;

        public Car getCar(Long id) {
                log.info("Fetching car with id {}", id);
                return carRepo.findById(id).orElseThrow();
        }

        public Car saveCar(Car car) {
                log.info("Saving new car {} {} to the database", car.getBrand(), car.getModel());
                return carRepo.save(car);
        }

        public void deleteCar(Long id) {
                log.info("Deleting car with id {}", id);
                carRepo.deleteById(id);
        }

        public List<Car> getAllCars() {
                log.info("Fetching all cars");
                return carRepo.findAll();
        }

        public List<CarPackage> getCarPackages() {
                log.info("Fetching all car packages");
                return carPackageRepo.findAll();
        }

        public CarPackage saveCarPackage(CarPackage carPackage) {
                log.info("Saving new package ({}) to the database", carPackage.getPackageName());
                return carPackageRepo.save(carPackage);
        }

        public void deleteCarPackage(Long id) {
                log.info("Deleting car package with id {}", id);
                carPackageRepo.deleteById(id);
        }

}
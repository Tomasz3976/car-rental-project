package com.example1.carrental.service;

import com.example1.carrental.domain.Car;
import com.example1.carrental.domain.CarPackage;
import com.example1.carrental.repo.CarPackageRepo;
import com.example1.carrental.repo.CarRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CarService {

        public static final int PAGE_SIZE = 10;
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

        public Car editCar(Car car) {
                Car carEdited = carRepo.findById(car.getId()).orElseThrow(() -> new EntityNotFoundException());
                log.info("Edition car with id {}", car.getId());
                carEdited.setRegistrationNr(car.getRegistrationNr());
                carEdited.setBrand(car.getBrand());
                carEdited.setModel(car.getModel());
                carEdited.setIsAvailable(car.getIsAvailable());
                carEdited.setCarPackage(car.getCarPackage());
                carEdited.setCarParameters(car.getCarParameters());
                return carRepo.save(carEdited);
        }

        public void deleteCar(Long id) {
                log.info("Deleting car with id {}", id);
                carRepo.deleteById(id);
        }

        public List<Car> getAllCars(Integer page, Sort.Direction sort) {
                log.info("Fetching all cars");
                return carRepo.findCars(PageRequest.of(page, PAGE_SIZE, Sort.by(sort, "id")));
        }

        public List<Car> getAvailableCars(Integer page, Sort.Direction sort) {
                log.info("Fetching available cars");
                return carRepo.findAvailableCars(PageRequest.of(page, PAGE_SIZE, Sort.by(sort, "id")));
        }

        public List<CarPackage> getCarPackages() {
                log.info("Fetching all car packages");
                return carPackageRepo.findAll();
        }

        public CarPackage saveCarPackage(CarPackage carPackage) {
                log.info("Saving new package {} to the database", carPackage.getPackageName());
                return carPackageRepo.save(carPackage);
        }

        public CarPackage editCarPackage(CarPackage carPackage) {
                CarPackage carPackageEdited = carPackageRepo.findById(carPackage.getId()).orElseThrow(() -> new EntityNotFoundException(carPackage.getPackageName()));
                log.info("Edition car package with id {}", carPackage.getId());
                carPackageEdited.setPackageName(carPackage.getPackageName());
                carPackageEdited.setPricePerHour(carPackage.getPricePerHour());
                return carPackageRepo.save(carPackageEdited);
        }

        public void deleteCarPackage(Long id) {
                log.info("Deleting car package with id {}", id);
                carPackageRepo.deleteById(id);
        }

}

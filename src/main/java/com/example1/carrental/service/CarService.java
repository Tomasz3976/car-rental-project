package com.example1.carrental.service;

import com.example1.carrental.domain.Car;
import com.example1.carrental.domain.CarPackage;
import com.example1.carrental.domain.PlacedOrder;
import com.example1.carrental.dto.CarEditDto;
import com.example1.carrental.dto.CarPackageDto;
import com.example1.carrental.dto.CarSaveDto;
import com.example1.carrental.exception.ExistingEntityException;
import com.example1.carrental.repo.CarPackageRepo;
import com.example1.carrental.repo.CarRepo;
import com.example1.carrental.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.example1.carrental.mapper.CarPackageDtoMapper.mapToCarPackage;
import static com.example1.carrental.mapper.CarSaveDtoMapper.mapToCar;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CarService {

        public static final int PAGE_SIZE = 10;
        private final CarRepo carRepo;
        private final CarPackageRepo carPackageRepo;
        private final OrderRepo orderRepo;

        public Car getCar(Long id) {
                log.info("Fetching car with id {}", id);
                return carRepo.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Car With This ID Does Not Exists!"));
        }

        public Car saveCar(CarSaveDto carSaveDto, String packageName) {
                CarPackage carPackage = carPackageRepo.findByPackageName(packageName)
                        .orElseThrow(() -> new EntityNotFoundException("This Package Does Not Exists!"));
                log.info("Saving new car {} {} to the database", carSaveDto.getBrand(), carSaveDto.getModel());
                Car car = mapToCar(carSaveDto);
                car.setCarPackage(carPackage);
                return carRepo.save(car);
        }

        public Car editCar(CarEditDto carEditDto) {
                Car carEdited = carRepo.findById(carEditDto.getId())
                        .orElseThrow(() -> new EntityNotFoundException("This Car Does Not Exists!"));
                log.info("Edition car with id {}", carEditDto.getId());
                carEdited.setRegistrationNr(carEditDto.getRegistrationNr());
                carEdited.setBrand(carEditDto.getBrand());
                carEdited.setModel(carEditDto.getModel());
                carEdited.setIsAvailable(carEditDto.getIsAvailable());
                carEdited.setCarParameters(carEditDto.getCarParameters());
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

        public CarPackage saveCarPackage(CarPackageDto carPackageDto) {
                if(carPackageRepo.findByPackageName(carPackageDto.getPackageName()).isPresent()) {

                        throw new ExistingEntityException("This Package Already Exists!");
                }
                log.info("Saving new package {} to the database", carPackageDto.getPackageName());
                return carPackageRepo.save(mapToCarPackage(carPackageDto));
        }

        public void deleteCarPackage(Long id) {
                log.info("Deleting car package with id {}", id);
                carPackageRepo.deleteById(id);
        }

        public List<PlacedOrder> getOrders() {
                log.info("Fetching all orders");
                return orderRepo.findAll();
        }

}

package com.example1.carrental.service;

import com.example1.carrental.domain.Car;
import com.example1.carrental.domain.CarPackage;
import com.example1.carrental.domain.PlacedOrder;
import com.example1.carrental.dto.CarPackageDto;
import com.example1.carrental.dto.CarDto;
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

        public Car saveCar(CarDto carDto, String packageName) {
                CarPackage carPackage = carPackageRepo.findByPackageName(packageName)
                        .orElseThrow(() -> new EntityNotFoundException("This Package Does Not Exists!"));
                log.info("Saving new car {} {} to the database", carDto.getBrand(), carDto.getModel());
                Car car = mapToCar(carDto);
                car.setCarPackage(carPackage);
                return carRepo.save(car);
        }

        public Car editCar(Long id, CarDto carDto) {
                Car carEdited = carRepo.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("This Car Does Not Exists!"));
                log.info("Edition car with id {}", id);
                carEdited.setRegistrationNr(carDto.getRegistrationNr());
                carEdited.setBrand(carDto.getBrand());
                carEdited.setModel(carDto.getModel());
                carEdited.setIsAvailable(carDto.getIsAvailable());
                carEdited.setCarParameters(carDto.getCarParameters());
                return carRepo.save(carEdited);
        }

        public void deleteCar(Long id) {
                log.info("Deleting car with id {}", id);
                carRepo.deleteById(id);
        }

        public List<Car> getAllCars(Integer page, Sort.Direction sort) {
                log.info("Fetching all cars");
                int pageNumber = page == null || page <= 0 ? 1 : page;
                Sort.Direction sortDirection = sort != null ? sort : Sort.Direction.ASC;
                return carRepo.findCars(PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.by(sortDirection, "id")));
        }

        public List<Car> getAvailableCars(Integer page, Sort.Direction sort) {
                log.info("Fetching available cars");
                int pageNumber = page == null || page <= 0 ? 1 : page;
                Sort.Direction sortDirection = sort != null ? sort : Sort.Direction.ASC;
                return carRepo.findAvailableCars(PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.by(sortDirection, "id")));
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

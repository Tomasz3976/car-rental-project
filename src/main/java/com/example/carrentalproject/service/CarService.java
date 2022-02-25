package com.example.carrentalproject.service;

import com.example.carrentalproject.domain.Car;
import com.example.carrentalproject.domain.CarPackage;
import com.example.carrentalproject.dto.CarDto;
import com.example.carrentalproject.dto.CarPackageDto;
import com.example.carrentalproject.repo.CarPackageRepository;
import com.example.carrentalproject.repo.CarRepository;
import com.example.carrentalproject.exception.ExistingEntityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.example.carrentalproject.mapper.CarPackageDtoMapper.mapToCarPackage;
import static com.example.carrentalproject.mapper.CarSaveDtoMapper.mapToCar;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CarService {

        public static final int DEFAULT_PAGE_SIZE = 10;
        private final CarRepository carRepository;
        private final CarPackageRepository carPackageRepository;

        public List<Car> getAllCars(Integer page, Sort.Direction sort) {
                log.info("Fetching all cars");
                int pageNumber = page == null || page <= 0 ? 1 : page;
                Sort.Direction sortDirection = sort != null ? sort : Sort.Direction.ASC;
                return carRepository.findCars(PageRequest.of(pageNumber - 1, DEFAULT_PAGE_SIZE, Sort.by(sortDirection, "id")));
        }

        public List<CarPackage> getCarPackages() {
                log.info("Fetching all car packages");
                return carPackageRepository.findAll();
        }

        public List<Car> getAvailableCars(Integer page, Sort.Direction sort) {
                log.info("Fetching available cars");
                int pageNumber = page == null || page <= 0 ? 1 : page;
                Sort.Direction sortDirection = sort != null ? sort : Sort.Direction.ASC;
                return carRepository.findAvailableCars(PageRequest.of(pageNumber - 1, DEFAULT_PAGE_SIZE, Sort.by(sortDirection, "id")));
        }

        public Car getCar(Long id) {
                log.info("Fetching car with id {}", id);
                return carRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Car With This ID Does Not Exists!"));
        }

        public Car saveCar(CarDto carDto, String packageName) {
                CarPackage carPackage = carPackageRepository.findByPackageName(packageName)
                        .orElseThrow(() -> new EntityNotFoundException("This Package Does Not Exists!"));
                log.info("Saving new car {} {} to the database", carDto.getBrand(), carDto.getModel());
                Car car = mapToCar(carDto);
                car.setCarPackage(carPackage);
                return carRepository.save(car);
        }

        public Car editCar(Long id, CarDto carDto) {
                Car carEdited = carRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("This Car Does Not Exists!"));
                log.info("Edition car with id {}", id);
                carEdited.setRegistrationNr(carDto.getRegistrationNr());
                carEdited.setBrand(carDto.getBrand());
                carEdited.setModel(carDto.getModel());
                carEdited.setIsAvailable(carDto.getIsAvailable());
                carEdited.setCarParameters(carDto.getCarParameters());
                return carRepository.save(carEdited);
        }

        public void deleteCar(Long id) {
                log.info("Deleting car with id {}", id);
                if(!carRepository.existsById(id)) throw new EntityNotFoundException("This Car Does Not Exists!");
                carRepository.deleteById(id);
        }

        public CarPackage saveCarPackage(CarPackageDto carPackageDto) {
                if(carPackageRepository.findByPackageName(carPackageDto.getPackageName()).isPresent()) {

                        throw new ExistingEntityException("This Package Already Exists!");
                }
                log.info("Saving new package {} to the database", carPackageDto.getPackageName());
                return carPackageRepository.save(mapToCarPackage(carPackageDto));
        }

        public void deleteCarPackage(Long id) {
                log.info("Deleting car package with id {}", id);
                if(!carPackageRepository.existsById(id)) throw new EntityNotFoundException("This Package Does Not Exists!");
                carPackageRepository.deleteById(id);
        }

}

package com.example.carrentalproject.service;

import com.example.carrentalproject.domain.Car;
import com.example.carrentalproject.domain.CarPackage;
import com.example.carrentalproject.domain.CarParameters;
import com.example.carrentalproject.dto.CarDto;
import com.example.carrentalproject.dto.CarPackageDto;
import com.example.carrentalproject.exception.ExistingEntityException;
import com.example.carrentalproject.repository.CarPackageRepository;
import com.example.carrentalproject.repository.CarParametersRepository;
import com.example.carrentalproject.repository.CarRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.carrentalproject.constant.FuelType.DIESEL;
import static com.example.carrentalproject.constant.GearBoxType.MANUAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

        @Mock
        CarRepository carRepository;

        @Mock
        CarPackageRepository carPackageRepository;

        @Mock
        CarParametersRepository carParametersRepository;

        @InjectMocks
        CarService carService;


        @Test()
        void itShouldReturnAnEmptyInstanceOfCar() {
                when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));

                Assertions.assertThat(carService.getCar(1L)).hasAllNullFieldsOrProperties();
        }

        @Test
        void itShouldSaveCar() {
                CarDto carDto = CarDto.builder()
                        .brand("Audi")
                        .model("A4")
                        .build();

                Car mapped = Car.builder()
                        .brand("Audi")
                        .model("A4")
                        .build();


                when(carRepository.save(mapped)).thenReturn(mapped);


                Car saved = carService.saveCar(carDto);

                assertThat(carDto.getModel()).isEqualTo(saved.getModel());
                assertThat(carDto.getBrand()).isEqualTo(saved.getBrand());
        }

        @Test
        void itShouldCheckIfCarIsEdited() {
                Long id = 2L;

                CarDto carDto = CarDto.builder()
                        .brand("Porsche")
                        .build();

                Car car = Car.builder()
                        .id(2L)
                        .brand("Porsche")
                        .build();


                when(carRepository.findById(id)).thenReturn(Optional.of(car));
                when(carRepository.save(car)).thenReturn(car);


                Assertions.assertThat(carService.editCar(id, carDto)).isEqualTo(car);
        }

        @Test
        void itShouldSetCarParameters() {
                Long id = 5L;

                CarParameters carParameters = CarParameters.builder()
                        .fuelType(DIESEL)
                        .gearBoxType(MANUAL)
                        .numberOfDoors(5)
                        .numberOfSeats(5)
                        .isAirConditioningAvailable(true)
                        .build();

                Car car = Car.builder()
                        .id(5L)
                        .registrationNr("WRT-60722")
                        .brand("Renault")
                        .model("Scenic")
                        .isAvailable(true)
                        .build();


                when(carRepository.findById(id)).thenReturn(Optional.of(car));
                when(carParametersRepository.save(carParameters)).thenReturn(carParameters);
                when(carRepository.save(car)).thenReturn(car);

                carService.setCarParameters(id, carParameters);


                assertThat(car.getCarParameters()).isEqualTo(carParameters);
        }

        @Test
        void itShouldSetCarPackage() {
                Long id = 8L;

                String packageName = "Sporty";

                Car car = Car.builder()
                        .id(5L)
                        .registrationNr("MRT-05006")
                        .brand("Ford")
                        .model("Fiesta")
                        .isAvailable(true)
                        .build();

                CarPackage carPackage = CarPackage.builder()
                        .packageName("Sporty")
                        .pricePerHour(300)
                        .build();


                when(carRepository.findById(id)).thenReturn(Optional.of(car));
                when(carPackageRepository.findByPackageName(packageName)).thenReturn(Optional.of(carPackage));
                when(carRepository.save(car)).thenReturn(car);

                carService.setCarPackage(id, packageName);


                assertThat(car.getCarPackage()).isEqualTo(carPackage);
        }

        @Test
        void itShouldDeleteCar() {
                Car car = Car.builder()
                        .id(4L)
                        .build();


                when(carRepository.existsById(4L)).thenReturn(true);
                doNothing().when(carRepository).deleteById(4L);


                carService.deleteCar(4L);

                verify(carRepository, times(1)).deleteById(4L);
        }

        @Test
        void itShouldReturnAllCars() {
                Car car1 = Car.builder()
                        .registrationNr("GHF88493")
                        .brand("Bentley")
                        .model("Continental")
                        .isAvailable(true)
                        .build();

                Car car2 = Car.builder()
                        .registrationNr("HGF78493")
                        .brand("Lamborghini")
                        .model("Huracan")
                        .isAvailable(true)
                        .build();

                Car car3 = Car.builder()
                        .registrationNr("KMN74837")
                        .brand("Volkswagen")
                        .model("Golf")
                        .isAvailable(false)
                        .build();

                List<Car> cars = new ArrayList<>();
                cars.add(car1);
                cars.add(car2);
                cars.add(car3);


                when(carRepository.findCars(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"))))
                .thenReturn(cars);


                Assertions.assertThat(carService.getAllCars(1, Sort.Direction.ASC)).hasSize(3);
        }

        @Test
        void itShouldReturnCarPackages() {
                CarPackage carPackage1 = new CarPackage();
                CarPackage carPackage2 = new CarPackage();
                List<CarPackage> list = Arrays.asList(carPackage1, carPackage2);


                when(carPackageRepository.findAll()).thenReturn(list);


                Assertions.assertThat(carService.getCarPackages()).isEqualTo(list);
        }

        @Test
        void itShouldSaveCarPackage() {
                CarPackageDto luxury = CarPackageDto.builder()
                        .packageName("Luxury")
                        .pricePerHour(500)
                        .build();

                CarPackage mapped = CarPackage.builder()
                        .packageName("Luxury")
                        .pricePerHour(500)
                        .cars(new ArrayList<>())
                        .build();


                when(carPackageRepository.save(mapped)).thenReturn(mapped);


                Assertions.assertThat(carService.saveCarPackage(luxury)).isEqualTo(mapped);
        }

        @Test
        void itShouldDeleteCarPackage() {
                CarPackage ordinary = CarPackage.builder()
                        .id(3L)
                        .packageName("Ordinary")
                        .pricePerHour(100)
                        .cars(new ArrayList<>())
                        .build();

                CarPackage awesome = CarPackage.builder()
                        .id(4L)
                        .packageName("Awesome")
                        .pricePerHour(800)
                        .cars(new ArrayList<>())
                        .build();


                when(carPackageRepository.existsById(3L)).thenReturn(true);
                when(carPackageRepository.existsById(4L)).thenReturn(true);
                when(carPackageRepository.getById(3L)).thenReturn(ordinary);
                when(carPackageRepository.getById(4L)).thenReturn(awesome);
                doNothing().when(carPackageRepository).delete(ordinary);
                doNothing().when(carPackageRepository).delete(awesome);


                carService.deleteCarPackage(3L);
                carService.deleteCarPackage(4L);

                verify(carPackageRepository, times(1)).delete(ordinary);
                verify(carPackageRepository, times(1)).delete(awesome);
        }

        @Test
        void itShouldThrowExistingPackageException() {
                CarPackageDto carPackageDto = CarPackageDto.builder()
                        .packageName("Sporty")
                        .build();

                CarPackage carPackage = CarPackage.builder()
                        .packageName("Sporty")
                        .build();

                when(carPackageRepository.findByPackageName("Sporty")).thenReturn(Optional.of(carPackage));

                assertThrows(ExistingEntityException.class, () -> carService.saveCarPackage(carPackageDto));
        }

        @Test
        void itShouldReturnAvailableCars() {
                Car available1 = Car.builder()
                        .registrationNr("OPE74639")
                        .brand("Audi")
                        .model("80")
                        .isAvailable(true)
                        .build();

                Car notAvailable1 = Car.builder()
                        .registrationNr("JKD94839")
                        .brand("Rolls-Royce")
                        .model("Phantom")
                        .isAvailable(false)
                        .build();

                Car available2 = Car.builder()
                        .registrationNr("HJD85743")
                        .brand("Fiat")
                        .model("Stilo")
                        .isAvailable(true)
                        .build();

                Car available3 = Car.builder()
                        .registrationNr("ASD84754")
                        .brand("Toyota")
                        .model("Yaris")
                        .isAvailable(true)
                        .build();

                Car notAvailable2 = Car.builder()
                        .registrationNr("OIU95840")
                        .brand("Opel")
                        .model("Insignia")
                        .isAvailable(false)
                        .build();

                List<Car> cars = new ArrayList<>();
                cars.add(available1);
                cars.add(notAvailable1);
                cars.add(available2);
                cars.add(available3);
                cars.add(notAvailable2);


                when(carRepository.findAvailableCars(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"))))
                        .thenReturn(cars.stream()
                                .filter(Car::getIsAvailable).collect(Collectors.toList()));


                Assertions.assertThat(carService.getAvailableCars(1, Sort.Direction.ASC)).hasSize(3);
        }

}
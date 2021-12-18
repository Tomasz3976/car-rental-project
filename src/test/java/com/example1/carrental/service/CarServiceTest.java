package com.example1.carrental.service;

import com.example1.carrental.domain.Car;
import com.example1.carrental.domain.CarPackage;
import com.example1.carrental.domain.PlacedOrder;
import com.example1.carrental.dto.CarPackageDto;
import com.example1.carrental.dto.CarDto;
import com.example1.carrental.exception.ExistingEntityException;
import com.example1.carrental.mapper.CarPackageDtoMapper;
import com.example1.carrental.mapper.CarSaveDtoMapper;
import com.example1.carrental.repo.CarPackageRepo;
import com.example1.carrental.repo.CarRepo;
import com.example1.carrental.repo.OrderRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

        @Mock
        CarRepo carRepo;

        @Mock
        CarPackageRepo carPackageRepo;

        @Mock
        OrderRepo orderRepo;

        @InjectMocks
        CarService carService;


        @Test()
        void itShouldReturnAnEmptyInstanceOfCar() {
                when(carRepo.findById(anyLong())).thenReturn(Optional.of(new Car()));

                assertThat(carService.getCar(1L)).hasAllNullFieldsOrProperties();
        }

        @Test
        void itShouldSaveCar() {
                CarDto carDto = CarDto.builder().brand("Audi").model("A4").build();
                String packageName = "Luxury";
                CarPackage luxury = CarPackage.builder().packageName("Luxury").build();

                when(carPackageRepo.findByPackageName("Luxury")).thenReturn(Optional.of(luxury));

                try (MockedStatic<CarSaveDtoMapper> mockedStatic = Mockito.mockStatic(CarSaveDtoMapper.class)) {

                        Car mapped = Car.builder().brand("Audi").model("A4").build();

                        mockedStatic.when(() -> CarSaveDtoMapper.mapToCar(carDto)).thenReturn(mapped);

                        when(carRepo.save(mapped)).thenReturn(mapped);

                        Car saved = carService.saveCar(carDto, packageName);

                        assertThat(carDto.getModel()).isEqualTo(saved.getModel());
                        assertThat(carDto.getBrand()).isEqualTo(saved.getBrand());
                }
        }

        @Test
        void itShouldCheckIfCarIsEdited() {
                Long id = 2L;
                CarDto carDto = CarDto.builder().brand("Porsche").build();
                Car car = Car.builder().id(2L).brand("Porsche").build();

                when(carRepo.findById(id)).thenReturn(Optional.of(car));
                when(carRepo.save(car)).thenReturn(car);

                assertThat(carService.editCar(id, carDto)).isEqualTo(car);
        }

        @Test
        void itShouldDeleteCar() {
                Car car = Car.builder().id(4L).build();
                doNothing().when(carRepo).deleteById(4L);

                carService.deleteCar(4L);

                verify(carRepo, times(1)).deleteById(4L);
        }

        @Test
        void itShouldReturnAllCars() {
                Car car = Car.builder().registrationNr("GHF88493").brand("Bentley").model("Continental").isAvailable(true).build();
                Car car2 = Car.builder().registrationNr("HGF78493").brand("Lamborghini").model("Huracan").isAvailable(true).build();
                Car car3 = Car.builder().registrationNr("KMN74837").brand("Volkswagen").model("Golf").isAvailable(false).build();
                List<Car> cars = new ArrayList<>();
                cars.add(car);
                cars.add(car2);
                cars.add(car3);

                when(carRepo.findCars(PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "id"))))
                .thenReturn(cars);

                assertThat(carService.getAllCars(1, Sort.Direction.ASC)).hasSize(3);
        }

        @Test
        void itShouldReturnCarPackages() {
                CarPackage carPackage1 = new CarPackage();
                CarPackage carPackage2 = new CarPackage();
                List<CarPackage> list = Arrays.asList(carPackage1, carPackage2);

                when(carPackageRepo.findAll()).thenReturn(list);

                assertThat(carService.getCarPackages()).isEqualTo(list);

        }

        @Test
        void itShouldSaveCarPackage() {
                CarPackageDto luxury = CarPackageDto.builder().packageName("Luxury").pricePerHour(500).build();

                try (MockedStatic<CarPackageDtoMapper> mockedStatic = Mockito.mockStatic(CarPackageDtoMapper.class)) {

                        CarPackage mapped = CarPackage.builder().packageName("Luxury").pricePerHour(500).build();

                        mockedStatic.when(() -> CarPackageDtoMapper.mapToCarPackage(luxury)).thenReturn(mapped);

                        when(carPackageRepo.save(mapped)).thenReturn(mapped);

                        CarPackage saved = carService.saveCarPackage(luxury);

                        assertThat(saved).isEqualTo(mapped);
                }
        }

        @Test
        void itShouldDeleteCarPackage() {
                CarPackage ordinary = CarPackage.builder().id(3L).packageName("Ordinary").pricePerHour(100).build();
                CarPackage awesome = CarPackage.builder().id(4L).packageName("Awesome").pricePerHour(800).build();

                doNothing().when(carPackageRepo).deleteById(3L);
                doNothing().when(carPackageRepo).deleteById(4L);

                carService.deleteCarPackage(3L);
                carService.deleteCarPackage(4L);

                verify(carPackageRepo, times(1)).deleteById(3L);
                verify(carPackageRepo, times(1)).deleteById(4L);
        }

        @Test
        void itShouldThrowExistingPackageException() {
                CarPackageDto carPackageDto = CarPackageDto.builder().packageName("Sporty").build();
                CarPackage carPackage = CarPackage.builder().packageName("Sporty").build();

                when(carPackageRepo.findByPackageName("Sporty")).thenReturn(Optional.of(carPackage));

                assertThrows(ExistingEntityException.class, () -> carService.saveCarPackage(carPackageDto));
        }

        @Test
        void itShouldReturnAvailableCars() {
                Car available1 = Car.builder().registrationNr("OPE74639").brand("Audi").model("80").isAvailable(true).build();
                Car notAvailable1 = Car.builder().registrationNr("JKD94839").brand("Rolls-Royce").model("Phantom").isAvailable(false).build();
                Car available2 = Car.builder().registrationNr("HJD85743").brand("Fiat").model("Stilo").isAvailable(true).build();
                Car available3 = Car.builder().registrationNr("ASD84754").brand("Toyota").model("Yaris").isAvailable(true).build();
                Car notAvailable2 = Car.builder().registrationNr("OIU95840").brand("Opel").model("Insignia").isAvailable(false).build();
                List<Car> cars = new ArrayList<>();
                cars.add(available1);
                cars.add(notAvailable1);
                cars.add(available2);
                cars.add(available3);
                cars.add(notAvailable2);

                when(carRepo.findAvailableCars(PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "id"))))
                        .thenReturn(cars.stream()
                                .filter(car -> car.getIsAvailable()).collect(Collectors.toList()));

                assertThat(carService.getAvailableCars(1, Sort.Direction.ASC)).hasSize(3);
        }

        @Test
        void itShouldReturnAllOrders() {
                PlacedOrder order = PlacedOrder.builder().userId(1L).carId(5L).brand("Ford").model("Mustang").build();
                PlacedOrder order2 = PlacedOrder.builder().userId(37L).carId(15L).brand("Fiat").model("Brava").build();
                PlacedOrder order3 = PlacedOrder.builder().userId(9L).carId(7L).brand("Daewoo").model("Matiz").build();
                PlacedOrder order4 = PlacedOrder.builder().userId(4L).carId(9L).brand("Porsche").model("Cayman").build();
                List<PlacedOrder> orders = new ArrayList<>();
                orders.add(order);
                orders.add(order2);
                orders.add(order3);
                orders.add(order4);

                when(orderRepo.findAll()).thenReturn(orders);

                assertThat(carService.getOrders()).isEqualTo(orders);
        }
}
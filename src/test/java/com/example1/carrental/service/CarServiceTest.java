package com.example1.carrental.service;

import com.example1.carrental.domain.Car;
import com.example1.carrental.domain.CarPackage;
import com.example1.carrental.repo.CarPackageRepo;
import com.example1.carrental.repo.CarRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

        @Mock
        CarRepo carRepo;

        @Mock
        CarPackageRepo carPackageRepo;

        @InjectMocks
        CarService carService;


        @Test()
        void itShouldReturnAnEmptyInstanceOfCar() {
                when(carRepo.findById(anyLong())).thenReturn(Optional.of(new Car()));

                assertThat(carService.getCar(1L)).hasAllNullFieldsOrProperties();
        }

        @Test
        void itShouldSaveCar() {
                Car car = new Car(null, "RBR15364", "Audi", "A4", true, null, null);

                when(carRepo.save(car)).thenReturn(car);

                Car saved = carService.saveCar(car);

                assertThat(car.getModel()).isEqualTo(saved.getModel());
                assertThat(car.getBrand()).isEqualTo(saved.getBrand());
        }

        @Test
        void itShouldCheckIfCarIsEdited() {
                Car car = new Car(2L, "GDA46573", "Fiat", "Panda", false, null, null);

                when(carRepo.findById(2L)).thenReturn(Optional.of(car));
                when(carRepo.save(car)).thenReturn(car);

                assertThat(carService.editCar(car)).isEqualTo(car);

        }

        @Test
        void itShouldDeleteCar() {
                Car car = new Car(4L, "WSR87688", "Chevrolet", "Camaro", true, null, null);

                doNothing().when(carRepo).deleteById(4L);

                carService.deleteCar(4L);

                verify(carRepo, times(1)).deleteById(4L);
        }

        @Test
        void itShouldReturnAllCars() {
                Car car = new Car(null, "GHF88493", "Bentley", "Continental", true, null, null);
                Car car2 = new Car(null, "HGF78493", "Lamborghini", "Huracan", true, null, null);
                Car car3 = new Car(null, "KMN74837", "Volkswagen", "Golf", false, null, null);
                ArrayList<Car> cars = new ArrayList<>();
                cars.add(car);
                cars.add(car2);
                cars.add(car3);

                when(carRepo.findCars(PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "id"))))
                .thenReturn(cars);

                assertThat(carService.getAllCars(1, Sort.Direction.ASC)).hasSize(3);
        }

        @Test
        void itShouldReturnAvailableCars() {
                Car available1 = new Car(null, "OPE74639", "Audi", "80", true, null, null);
                Car notAvailable1 = new Car(null, "JKD94839", "Rolls-Royce", "Phantom", false, null, null);
                Car available2 = new Car(null, "HJD85743", "Fait", "Stilo", true, null, null);
                Car available3 = new Car(null, "ASD84754", "Toyota", "Yaris", true, null, null);
                Car notAvailable2 = new Car(null, "OIU95840", "Opel", "Insignia", false, null, null);
                ArrayList<Car> cars = new ArrayList<>();
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
        void itShouldReturnCarPackages() {
                CarPackage carPackage1 = new CarPackage();
                CarPackage carPackage2 = new CarPackage();
                List<CarPackage> list = Arrays.asList(carPackage1, carPackage2);

                when(carPackageRepo.findAll()).thenReturn(list);

                assertThat(carService.getCarPackages()).isEqualTo(list);

        }

        @Test
        void itShouldSaveCarPackage() {
                CarPackage luxury = new CarPackage(null, "Luxury", 500);

                when(carPackageRepo.save(luxury)).thenReturn(luxury);

                CarPackage saved = carService.saveCarPackage(luxury);

                assertThat(saved).isEqualTo(luxury);
        }

        @Test
        void itShouldCheckIfPackageIsEdited() {
                CarPackage sporty = new CarPackage(5L, "Sporty", 300);

                when(carPackageRepo.findById(5L)).thenReturn(Optional.of(sporty));
                when(carPackageRepo.save(sporty)).thenReturn(sporty);

                assertThat(carService.editCarPackage(sporty)).isEqualTo(sporty);
        }

        @Test
        void itShouldDeleteCarPackage() {
                CarPackage ordinary = new CarPackage(3L, "Ordinary", 100);
                CarPackage awesome = new CarPackage(4L, "Awesome", 800);

                doNothing().when(carPackageRepo).deleteById(3L);
                doNothing().when(carPackageRepo).deleteById(4L);

                carService.deleteCarPackage(3L);
                carService.deleteCarPackage(4L);

                verify(carPackageRepo, times(1)).deleteById(3L);
                verify(carPackageRepo, times(1)).deleteById(4L);
        }

}
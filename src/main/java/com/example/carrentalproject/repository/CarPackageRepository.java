package com.example.carrentalproject.repository;

import com.example.carrentalproject.domain.CarPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarPackageRepository extends JpaRepository<CarPackage, Long> {

        Optional<CarPackage> findByPackageName(String name);

}

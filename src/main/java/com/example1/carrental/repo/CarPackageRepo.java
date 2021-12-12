package com.example1.carrental.repo;

import com.example1.carrental.domain.CarPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarPackageRepo extends JpaRepository<CarPackage, Long> {

        Optional<CarPackage> findByPackageName(String name);

}

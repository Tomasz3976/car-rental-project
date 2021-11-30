package com.example1.carrental.repo;

import com.example1.carrental.domain.CarPackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarPackageRepo extends JpaRepository<CarPackage, Long> {
}

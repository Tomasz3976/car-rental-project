package com.example.carrentalproject.repo;

import com.example.carrentalproject.domain.CarParameters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarParametersRepo extends JpaRepository<CarParameters, Long> {
}

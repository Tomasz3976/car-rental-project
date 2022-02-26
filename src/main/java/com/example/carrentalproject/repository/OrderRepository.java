package com.example.carrentalproject.repository;

import com.example.carrentalproject.domain.PlacedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<PlacedOrder, Long> {}

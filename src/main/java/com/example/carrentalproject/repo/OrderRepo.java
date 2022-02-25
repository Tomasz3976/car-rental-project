package com.example.carrentalproject.repo;

import com.example.carrentalproject.domain.PlacedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<PlacedOrder, Long> {}

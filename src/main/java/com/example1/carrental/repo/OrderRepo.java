package com.example1.carrental.repo;

import com.example1.carrental.domain.PlacedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<PlacedOrder, Long> {}

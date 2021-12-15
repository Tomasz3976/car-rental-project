package com.example1.carrental.repo;

import com.example1.carrental.domain.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepo extends JpaRepository<CreditCard, Long> {
}

package com.example.carrentalproject.repo;

import com.example.carrentalproject.domain.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepo extends JpaRepository<CreditCard, Long> {
}

package com.example.carrentalproject.repository;

import com.example.carrentalproject.domain.AccessKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessKeyRepository extends JpaRepository<AccessKey, Long> {
}

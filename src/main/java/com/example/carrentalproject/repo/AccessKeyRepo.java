package com.example.carrentalproject.repo;

import com.example.carrentalproject.domain.AccessKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessKeyRepo extends JpaRepository<AccessKey, Long> {
}

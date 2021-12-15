package com.example1.carrental.repo;

import com.example1.carrental.domain.AccessKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessKeyRepo extends JpaRepository<AccessKey, Long> {
}

package com.example.geocaching1.repository;

import com.example.geocaching1.entity.FoundStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoundStatusRepository extends JpaRepository<FoundStatus, Integer> {
    List<FoundStatus> findByUserId(Integer userId);
    Optional<FoundStatus> findByUserIdAndGeocacheCode(Integer userId, String geocacheCode);
}

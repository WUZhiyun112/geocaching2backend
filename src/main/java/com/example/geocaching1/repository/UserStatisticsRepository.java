package com.example.geocaching1.repository;

import com.example.geocaching1.entity.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Integer> {
}

package com.example.geocaching1.repository;

import com.example.geocaching1.entity.Geocache;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeocacheRepository extends JpaRepository<Geocache, Integer> {
    List<Geocache> findByUserId(Integer userId);
}

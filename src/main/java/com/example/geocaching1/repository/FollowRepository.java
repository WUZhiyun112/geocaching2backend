package com.example.geocaching1.repository;

import com.example.geocaching1.entity.Follow;
import com.example.geocaching1.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;



public interface FollowRepository extends JpaRepository<Follow, Long> {

    // Using User entity instead of userId
    Optional<Follow> findByUserAndGeocacheCode(User user, String geocacheCode);

    List<Follow> findByUserAndIsFollowedTrue(User user);
}

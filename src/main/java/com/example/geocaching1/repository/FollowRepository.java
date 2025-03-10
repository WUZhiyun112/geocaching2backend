package com.example.geocaching1.repository;

import com.example.geocaching1.entity.Follow;
import com.example.geocaching1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 使用 User 实体而不是 userId
    Optional<Follow> findByUserAndGeocacheCode(User user, String geocacheCode);

    List<Follow> findByUserAndIsFollowedTrue(User user);
}

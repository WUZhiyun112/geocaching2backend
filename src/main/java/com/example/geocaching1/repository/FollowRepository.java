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

    // 新的查询方法：根据 Geocache 名称查找
    List<Follow> findByGeocacheName(String geocacheName);

    // 新的查询方法：根据 Geocache 类型查找
    List<Follow> findByGeocacheType(String geocacheType);

    // 新的查询方法：根据 Geocache 位置查找
    List<Follow> findByLocation(String location);

    // 根据 Geocache 名称和类型查找
    List<Follow> findByGeocacheNameAndGeocacheType(String geocacheName, String geocacheType);

    // 根据 Geocache 位置和类型查找
    List<Follow> findByLocationAndGeocacheType(String location, String geocacheType);
}

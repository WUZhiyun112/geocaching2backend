package com.example.geocaching1.service;

import com.example.geocaching1.entity.Follow;
import com.example.geocaching1.entity.User;
import com.example.geocaching1.repository.FollowRepository;
import com.example.geocaching1.repository.UserRepository; // 需要引入 UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;  // 引入 UserRepository 来查找 User 实体

    @Transactional
    public Follow addFollow(Integer userId, String geocacheCode, String geocacheName, String geocacheType, String location) {
        // 根据 userId 查找 User 实体
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            // 如果找不到用户，抛出异常
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        // 检查用户是否已经关注该 geocache
        Optional<Follow> existingFollow = followRepository.findByUserAndGeocacheCode(user.get(), geocacheCode);
        if (existingFollow.isPresent()) {
            // 如果已关注，返回已存在的关注记录
            return existingFollow.get();
        }

        // 创建新关注记录
        Follow follow = new Follow();
        follow.setUser(user.get());  // 设置 User 实体
        follow.setGeocacheCode(geocacheCode);
        follow.setIsFollowed(true); // 设置为已关注
        follow.setGeocacheName(geocacheName);  // 设置 Geocache 名称
        follow.setGeocacheType(geocacheType);  // 设置 Geocache 类型
        follow.setLocation(location);          // 设置 Geocache 位置

        try {
            // 保存并返回关注记录
            return followRepository.save(follow);
        } catch (DataIntegrityViolationException e) {
            // 处理数据完整性冲突，可能是重复插入等问题
            throw new RuntimeException("Error saving follow record", e);
        }
    }
    public Follow removeFollow(Integer userId, String geocacheCode) {
        // 根据 userId 查找 User 实体
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            // 如果找不到用户，返回 null 或抛出异常
            return null;
        }

        // 查找关注记录
        Optional<Follow> follow = followRepository.findByUserAndGeocacheCode(user.get(), geocacheCode);
        if (follow.isPresent()) {
            follow.get().setIsFollowed(false); // 设置为已取消关注，使用 0
            return followRepository.save(follow.get());
        }
        return null;
    }


    public List<Follow> getFollowedGeocaches(Integer userId) {
        // 根据 userId 查找 User 实体
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            // 如果找不到用户，返回空列表或抛出异常
            return List.of();
        }

        // 查找已关注的 geocache
        return followRepository.findByUserAndIsFollowedTrue(user.get());
    }
}

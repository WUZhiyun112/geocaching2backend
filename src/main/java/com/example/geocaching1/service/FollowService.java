package com.example.geocaching1.service;

import com.example.geocaching1.entity.Follow;
import com.example.geocaching1.entity.User;
import com.example.geocaching1.repository.FollowRepository;
import com.example.geocaching1.repository.UserRepository; // 需要引入 UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;  // 引入 UserRepository 来查找 User 实体

    public Follow addFollow(Integer userId, String geocacheCode) {
        // 根据 userId 查找 User 实体
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            // 如果找不到用户，返回 null 或抛出异常
            return null;
        }

        // 检查用户是否已经关注该 geocache
        Optional<Follow> existingFollow = followRepository.findByUserAndGeocacheCode(user.get(), geocacheCode);
        if (existingFollow.isPresent()) {
            return existingFollow.get();
        }

        // 创建新关注记录
        Follow follow = new Follow();
        follow.setUser(user.get());  // 设置 User 实体
        follow.setGeocacheCode(geocacheCode);
        follow.setIsFollowed(true); // 设置为已关注
        return followRepository.save(follow);
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

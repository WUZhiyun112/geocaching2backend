package com.example.geocaching1.service;

import com.example.geocaching1.dto.GeocacheInfoDTO;
import com.example.geocaching1.entity.Follow;
import com.example.geocaching1.entity.Geocache;
import com.example.geocaching1.entity.User;
import com.example.geocaching1.repository.FollowRepository;
import com.example.geocaching1.repository.GeocacheRepository;
import com.example.geocaching1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final GeocacheRepository geocacheRepository;

    @Autowired
    public FollowService(FollowRepository followRepository, UserRepository userRepository, GeocacheRepository geocacheRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
        this.geocacheRepository = geocacheRepository;
    }

    public Follow addFollow(Integer userId, String geocacheCode) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            System.out.println("User not found for id: " + userId); // 打印日志
            return null;
        }

        Optional<Follow> existingFollow = followRepository.findByUserAndGeocacheCode(user.get(), geocacheCode);
        if (existingFollow.isPresent()) {
            return existingFollow.get();
        }

        Follow follow = new Follow();
        follow.setUser(user.get());
        follow.setGeocacheCode(geocacheCode);
        follow.setIsFollowed(true);
        return followRepository.save(follow);
    }

    public Follow removeFollow(Integer userId, String geocacheCode) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            System.out.println("User not found for id: " + userId); // 打印日志
            return null;
        }

        Optional<Follow> follow = followRepository.findByUserAndGeocacheCode(user.get(), geocacheCode);
        if (follow.isPresent()) {
            follow.get().setIsFollowed(false);
            return followRepository.save(follow.get());
        }
        return null;
    }

    public List<Geocache> getFollowedGeocaches(List<String> geocacheCodes) {
        List<Geocache> followedGeocaches = new ArrayList<>();

        for (String code : geocacheCodes) {
            // 调用外部API获取地理缓存详情
            String response = GeocacheFetcher.fetchGeocacheDetails(code);

            // 检查响应是否为空或存在错误信息
            if (response != null && !response.isEmpty()) {
                Geocache geocache = GeocacheFetcher.parseGeocacheDetails(response);

                // 如果解析成功，添加到列表
                if (geocache != null) {
                    followedGeocaches.add(geocache);
                } else {
                    // 解析失败时的日志
                    Log.e("GeocacheFetcher", "Failed to parse geocache details for code: " + code);
                }
            } else {
                // API请求失败时的日志
                Log.e("GeocacheFetcher", "API response is empty or failed for geocache code: " + code);
            }
        }

        // 返回所有获取到的已关注地理缓存
        return followedGeocaches;
    }

}
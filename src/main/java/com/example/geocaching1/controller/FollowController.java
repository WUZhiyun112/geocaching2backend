package com.example.geocaching1.controller;

import com.example.geocaching1.entity.Follow;
import com.example.geocaching1.entity.User;
import com.example.geocaching1.repository.UserRepository;
import com.example.geocaching1.repository.FollowRepository;
import com.example.geocaching1.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    @Autowired
    private FollowService followService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;


    // 用户关注 geocache
    @PostMapping("/add")
    public ResponseEntity<String> followGeocache(@RequestParam Integer userId, @RequestParam String geocacheCode) {
        if (userId == null || geocacheCode == null || geocacheCode.isEmpty()) {
            return new ResponseEntity<>("Invalid parameters", HttpStatus.BAD_REQUEST);
        }

        // 检查用户是否关注成功
        Follow follow = followService.addFollow(userId, geocacheCode);
        if (follow != null) {
            return new ResponseEntity<>("Followed geocache successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Geocache already followed or error", HttpStatus.BAD_REQUEST);
    }

    // 取消关注 geocache
    @PostMapping("/remove")
    public Follow removeFollow(Integer userId, String geocacheCode) {
        // 清理 geocacheCode 中的空格和换行符
        geocacheCode = geocacheCode.trim().replaceAll("\\n", "");

        // 根据 userId 查找 User 实体
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            System.out.println("User not found");
            return null;
        }

        // 查找关注记录
        Optional<Follow> follow = followRepository.findByUserAndGeocacheCode(user.get(), geocacheCode);
        if (follow.isPresent()) {
            System.out.println("Found Follow: " + follow.get());  // 打印找到的记录
            follow.get().setIsFollowed(false);  // 设置为已取消关注
            return followRepository.save(follow.get());  // 保存修改后的记录
        } else {
            System.out.println("No Follow found for geocache: " + geocacheCode);  // 打印未找到记录
        }
        return null;
    }



    // 获取用户关注的 geocache 列表
    @GetMapping("/list")
    public ResponseEntity<List<Follow>> getUserFollowedGeocaches(@RequestParam Integer userId) {
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // 获取用户所有已关注的 geocache
        List<Follow> followedGeocaches = followService.getFollowedGeocaches(userId);
        if (followedGeocaches.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(followedGeocaches, HttpStatus.OK);
    }
}

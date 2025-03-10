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
    public ResponseEntity<Follow> followGeocache(@RequestParam Integer userId, @RequestParam String geocacheCode) {
        if (userId == null || geocacheCode == null || geocacheCode.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // 检查用户是否关注成功
        Follow follow = followService.addFollow(userId, geocacheCode);
        if (follow != null) {
            return new ResponseEntity<>(follow, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    // 取消关注 geocache
// 取消关注 geocache
    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFollow(@RequestParam Integer userId, @RequestParam String geocacheCode) {
        // 清理 geocacheCode 中的空格和换行符
        geocacheCode = geocacheCode.trim().replaceAll("\\n", "");

        // 根据 userId 查找 User 实体
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 用户未找到，返回404
        }

        // 查找关注记录
        Optional<Follow> follow = followRepository.findByUserAndGeocacheCode(user.get(), geocacheCode);
        if (follow.isPresent()) {
            // 删除关注记录
            followRepository.delete(follow.get());  // 删除记录
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 返回204 No Content，表示删除成功
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 没有找到关注记录，返回404
        }
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

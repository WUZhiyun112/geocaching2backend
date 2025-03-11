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
    public ResponseEntity<Follow> followGeocache(
            @RequestParam Integer userId,
            @RequestParam String geocacheCode,
            @RequestParam String geocacheName,
            @RequestParam String geocacheType,
            @RequestParam String location) {

        // 如果有任何参数为空，返回 400 错误
        if (userId == null || geocacheCode == null || geocacheCode.isEmpty() ||
                geocacheName == null || geocacheType == null || location == null) {
            // 返回一个空的 Follow 对象，并附带错误消息
            Follow errorFollow = new Follow();
            errorFollow.setGeocacheName("Missing required parameter(s)");
            return new ResponseEntity<>(errorFollow, HttpStatus.BAD_REQUEST);
        }

        // 调用服务层的 addFollow 方法
        Follow follow = followService.addFollow(userId, geocacheCode, geocacheName, geocacheType, location);

        // 如果关注成功，返回 201 状态和 Follow 对象
        if (follow != null) {
            return new ResponseEntity<>(follow, HttpStatus.CREATED);
        }

        // 如果没有成功，返回一个空的 Follow 对象
        Follow errorFollow = new Follow();
        errorFollow.setGeocacheName("Failed to follow geocache");
        return new ResponseEntity<>(errorFollow, HttpStatus.BAD_REQUEST);
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

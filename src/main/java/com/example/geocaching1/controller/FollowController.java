package com.example.geocaching1.controller;

import com.example.geocaching1.dto.GeocacheInfoDTO;
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

    @PostMapping("/add")
    public ResponseEntity<Follow> followGeocache(@RequestParam Integer userId, @RequestParam String geocacheCode) {
        if (userId == null || geocacheCode == null || geocacheCode.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Follow follow = followService.addFollow(userId, geocacheCode);
        if (follow != null) {
            return new ResponseEntity<>(follow, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFollow(@RequestParam Integer userId, @RequestParam String geocacheCode) {
        geocacheCode = geocacheCode.trim().replaceAll("\\n", "");

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Follow> follow = followRepository.findByUserAndGeocacheCode(user.get(), geocacheCode);
        if (follow.isPresent()) {
            followRepository.delete(follow.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<GeocacheInfoDTO>> getUserFollowedGeocaches(@RequestParam Integer userId) {
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<GeocacheInfoDTO> followedGeocaches = followService.getFollowedGeocaches(userId);
        System.out.println("Followed geocaches: " + followedGeocaches); // 打印日志

        if (followedGeocaches.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(followedGeocaches, HttpStatus.OK);
    }
}
package com.example.geocaching1.controller;

import com.example.geocaching1.entity.FoundStatus;
import com.example.geocaching1.service.FoundStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.time.LocalDateTime;
@RestController
@RequestMapping("/api/foundstatus")

public class FoundStatusController {

    @Autowired
    private FoundStatusService foundStatusService;

    // 设置 geocache 的找到状态
    @PostMapping("/set")
    public ResponseEntity<FoundStatus> setFoundStatus(
            @RequestParam Integer userId,
            @RequestParam String geocacheCode,
            @RequestParam String geocacheName,
            @RequestParam String geocacheType,
            @RequestParam String location,
            @RequestParam String myStatus,
            @RequestParam(required = false) String foundAt) {  // Allow foundAt to be passed

        System.out.println("Received userId: " + userId);
        System.out.println("Received geocacheCode: " + geocacheCode);
        System.out.println("Received geocacheName: " + geocacheName);
        System.out.println("Received geocacheType: " + geocacheType);
        System.out.println("Received location: " + location);
        System.out.println("Received myStatus: '" + myStatus + "'");
        System.out.println("Received foundAt: " + foundAt);  // Print foundAt

        if (userId == null || geocacheCode == null || geocacheCode.isEmpty() || geocacheName == null ||
                geocacheType == null || location == null || myStatus == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!myStatus.equals("Haven’t started") && !myStatus.equals("Found it") && !myStatus.equals("Searched but not found")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Parse foundAt if it's passed
        LocalDateTime foundAtTime = null;
        if (foundAt != null && !foundAt.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                foundAtTime = LocalDateTime.parse(foundAt, formatter);
            } catch (DateTimeParseException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        FoundStatus foundStatus = foundStatusService.setFoundStatus(userId, geocacheCode, geocacheName, geocacheType, location, myStatus, foundAtTime);

        return new ResponseEntity<>(foundStatus, HttpStatus.CREATED);
    }


    // 获取用户找到的 geocache 列表
    @GetMapping("/list")
    public ResponseEntity<List<FoundStatus>> getUserFoundGeocaches(@RequestParam Integer userId) {
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<FoundStatus> foundGeocaches = foundStatusService.getUserFoundGeocaches(userId);
        return foundGeocaches.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(foundGeocaches, HttpStatus.OK);
    }
}

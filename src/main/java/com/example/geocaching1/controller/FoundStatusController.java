package com.example.geocaching1.controller;

import com.example.geocaching1.entity.FoundStatus;
import com.example.geocaching1.service.FoundStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam String myStatus) {

        System.out.println("Received userId: " + userId);
        System.out.println("Received geocacheCode: " + geocacheCode);
        System.out.println("Received geocacheName: " + geocacheName);
        System.out.println("Received geocacheType: " + geocacheType);
        System.out.println("Received location: " + location);
        System.out.println("Received myStatus: '" + myStatus + "'");  // 加单引号检查空格或换行

        if (userId == null || geocacheCode == null || geocacheCode.isEmpty() || geocacheName == null ||
                geocacheType == null || location == null || myStatus == null) {
            System.out.println("Validation failed: Some parameters are null or empty.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // 打印参数
        System.out.println("Received parameters: userId=" + userId + ", geocacheCode=" + geocacheCode +
                ", geocacheName=" + geocacheName + ", geocacheType=" + geocacheType +
                ", location=" + location + ", myStatus=" + myStatus);

        // 空值检查
        if (userId == null || geocacheCode == null || geocacheCode.isEmpty() || geocacheName == null ||
                geocacheType == null || location == null || myStatus == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // 只允许三种状态
        if (!myStatus.equals("Not Found") && !myStatus.equals("Found") && !myStatus.equals("Found Fail")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // 调用 service 层方法保存找到状态
        FoundStatus foundStatus = foundStatusService.setFoundStatus(userId, geocacheCode, geocacheName, geocacheType, location, myStatus);

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

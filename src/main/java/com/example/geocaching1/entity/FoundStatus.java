package com.example.geocaching1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class FoundStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer found_status_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String geocacheCode;
    private String geocacheName;
    private String geocacheType;
    private String location;
    private String myStatus; // "未找到", "已找到"

    @Column(nullable = true) // 允许为空
    private LocalDateTime foundAt; // 仅当 status 为 "已找到" 时有值，否则为 null

    // Getters and Setters
    public Integer getId() {
        return found_status_id;
    }

    public void setId(Integer id) {
        this.found_status_id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGeocacheCode() {
        return geocacheCode;
    }

    public void setGeocacheCode(String geocacheCode) {
        this.geocacheCode = geocacheCode;
    }

    public String getGeocacheName() {
        return geocacheName;
    }

    public void setGeocacheName(String geocacheName) {
        this.geocacheName = geocacheName;
    }

    public String getGeocacheType() {
        return geocacheType;
    }

    public void setGeocacheType(String geocacheType) {
        this.geocacheType = geocacheType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return myStatus;
    }

    public void setStatus(String status) {
        this.myStatus = status;
    }

    public LocalDateTime getFoundAt() {
        return foundAt;
    }

    public void setFoundAt(LocalDateTime foundAt) {
        this.foundAt = foundAt;
    }
}

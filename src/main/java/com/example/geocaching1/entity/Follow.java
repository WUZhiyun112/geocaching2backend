package com.example.geocaching1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 关联用户表

    private String geocacheCode;  // 外部数据源的 geocache 唯一标识符
    private String geocacheName;  // Geocache 名称
    private String geocacheType;  // Geocache 类型
    private String location;      // Geocache 位置

    private boolean isFollowed = true;  // 默认值为 true，表示关注

    // Modify getters and setters for the new fields
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

    // Existing methods
    public Integer getUserId() {
        return user != null ? user.getId() : null;
    }

    public void setUserId(Integer id) {
        if (this.user == null) {
            this.user = new User();
        }
        this.user.setId(id);
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

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(boolean followed) {
        isFollowed = followed;
    }
}

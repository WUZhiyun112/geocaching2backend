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

    private boolean isFollowed = true;  // 默认值为 true，表示关注

    // 修改为 Integer 类型
    public Integer getUserId() {
        return user != null ? user.getId() : null;  // 返回与 User 对应的 user_id
    }

    public void setUserId(Integer id) {
        if (this.user == null) {
            this.user = new User();  // 创建一个新的 User 实例
        }
        this.user.setId(id);  // 将传入的 id 设置为 User 对象的 user_id
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

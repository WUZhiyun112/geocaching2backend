package com.example.geocaching1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Table(name = "geocache")
public class Geocache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer geocache_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "latitude", precision = 9, scale = 6)
    private BigDecimal latitude;

    @NotNull
    @Column(name = "longitude", precision = 9, scale = 6)
    private BigDecimal longitude;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "type")
    private String type;

    @NotBlank
    @Column(name = "status")
    private String status;

    @Column(name = "found_at", updatable = false)
    private LocalDateTime foundAt = LocalDateTime.now();

    @Column(name = "difficulty")
    private String difficulty;

    @NotBlank
    @Column(name = "code", unique = true)
    private String code;

    public Geocache(String code, String name, BigDecimal latitude, BigDecimal longitude, String status, String type, LocalDateTime foundAt, String difficulty) {
        this.code = code;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.type = type;
        this.foundAt = foundAt;
        this.difficulty = difficulty;
    }

    // Getter and setter for difficulty
    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    // Getter and setter for code
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    // Other getters and setters
    public Integer getId() {
        return geocache_id;
    }

    public void setId(Integer id) {
        this.geocache_id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getFoundAt() {
        return foundAt;
    }

    public void setFoundAt(LocalDateTime foundAt) {
        this.foundAt = foundAt;
    }

    @Override
    public String toString() {
        return "Geocache{" +
                "id=" + geocache_id +
                ", user=" + user.getUsername() +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", foundAt=" + foundAt +
                ", code='" + code + '\'' +
                ", difficulty='" + difficulty + '\'' +
                '}';
    }
}

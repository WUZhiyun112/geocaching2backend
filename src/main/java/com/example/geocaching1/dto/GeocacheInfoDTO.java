package com.example.geocaching1.dto;


public class GeocacheInfoDTO {
    private String code;
    private String difficulty;
    private String name;

    // 构造函数
    public GeocacheInfoDTO(String code, String difficulty, String name) {
        this.code = code;
        this.difficulty = difficulty;
        this.name = name;
    }

    // Getters 和 Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

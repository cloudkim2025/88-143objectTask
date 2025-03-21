package com.example.objecttesk.domain;

public class User {
    private String id;
    private String name;
    private String password;

    // 기본 생성자
    public User() {}

    // getter 및 setter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

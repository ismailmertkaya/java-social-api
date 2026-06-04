package com.example.social.model;

import java.time.LocalDateTime;

public class Comment {
    private Long userId;
    private String username;
    private String text;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Comment() {}

    public Comment(Long userId, String username, String text) {
        this.userId = userId;
        this.username = username;
        this.text = text;
    }

    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getText() { return text; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}

package com.example.social.model;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.*;


public class Post {

    private Long id;
    private Long authorId;
    private String authorUsername;

    @NotBlank
    @Size(max = 280, message = "Post cannot exceed 280 characters")
    private String content;

    private List<String> hashtags = new ArrayList<>();
    private Set<Long> likedByUserIds = new HashSet<>();
    private List<Comment> comments = new ArrayList<>();
    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean deleted = false;

    public Post() {}

    public Post(Long id, Long authorId, String authorUsername, String content) {
        this.id = id;
        this.authorId = authorId;
        this.authorUsername = authorUsername;
        this.content = content;
        this.hashtags = extractHashtags(content);
    }

    public boolean like(Long userId) {
        return likedByUserIds.add(userId); 
    }

    public boolean unlike(Long userId) {
        return likedByUserIds.remove(userId);
    }

    public int getLikeCount() { return likedByUserIds.size(); }

    public void addComment(Comment comment) { comments.add(comment); }

  
    private List<String> extractHashtags(String text) {
        List<String> tags = new ArrayList<>();
        if (text == null) return tags;
        for (String word : text.split("\\s+")) {
            if (word.startsWith("#") && word.length() > 1) {
                tags.add(word.toLowerCase());
            }
        }
        return tags;
    }

  
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
    public String getAuthorUsername() { return authorUsername; }
    public void setAuthorUsername(String u) { this.authorUsername = u; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public List<String> getHashtags() { return hashtags; }
    public Set<Long> getLikedByUserIds() { return likedByUserIds; }
    public List<Comment> getComments() { return comments; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}

package com.example.social.model;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * User — a registered member of the platform.
 */
public class User {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 30)
    private String username;

    @NotBlank
    @Email
    private String email;

    private String bio;
    private String profilePicUrl;
    private boolean active = true;
    private LocalDateTime joinedAt = LocalDateTime.now();

    // IDs of users this user follows
    private Set<Long> following = new HashSet<>();
    // IDs of users who follow this user
    private Set<Long> followers = new HashSet<>();

    public User() {}

    public User(Long id, String username, String email, String bio) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.bio = bio;
    }

    public int getFollowingCount() { return following.size(); }
    public int getFollowersCount() { return followers.size(); }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getProfilePicUrl() { return profilePicUrl; }
    public void setProfilePicUrl(String url) { this.profilePicUrl = url; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public LocalDateTime getJoinedAt() { return joinedAt; }
    public Set<Long> getFollowing() { return following; }
    public void setFollowing(Set<Long> following) { this.following = following; }
    public Set<Long> getFollowers() { return followers; }
    public void setFollowers(Set<Long> followers) { this.followers = followers; }
}

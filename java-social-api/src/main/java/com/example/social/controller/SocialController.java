package com.example.social.controller;

import com.example.social.model.*;
import com.example.social.service.SocialService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SocialController {

    private final SocialService service;

    public SocialController(SocialService service) {
        this.service = service;
    }

    // ── Users ─────────────────────────────────────────────────────

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(service.getUser(id));
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody Map<String, String> body) {
        User user = service.createUser(body.get("username"), body.get("email"), body.get("bio"));
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/users/{id}/follow/{targetId}")
    public ResponseEntity<Map<String, String>> follow(@PathVariable Long id, @PathVariable Long targetId) {
        service.follow(id, targetId);
        return ResponseEntity.ok(Map.of("message", "Followed successfully"));
    }

    @DeleteMapping("/users/{id}/follow/{targetId}")
    public ResponseEntity<Map<String, String>> unfollow(@PathVariable Long id, @PathVariable Long targetId) {
        service.unfollow(id, targetId);
        return ResponseEntity.ok(Map.of("message", "Unfollowed"));
    }

    // ── Posts ─────────────────────────────────────────────────────

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts(
            @RequestParam(required = false) String hashtag) {
        if (hashtag != null) return ResponseEntity.ok(service.searchByHashtag(hashtag));
        return ResponseEntity.ok(service.getAllPosts());
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPost(id));
    }

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<List<Post>> getUserPosts(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getPostsByUser(userId));
    }

    @GetMapping("/users/{userId}/feed")
    public ResponseEntity<List<Post>> getFeed(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getFeed(userId));
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody Map<String, Object> body) {
        Post post = service.createPost(
                Long.parseLong(body.get("authorId").toString()),
                (String) body.get("content")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<Post> likePost(@PathVariable Long postId,
                                         @RequestBody Map<String, Long> body) {
        return ResponseEntity.ok(service.likePost(postId, body.get("userId")));
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Post> comment(@PathVariable Long postId,
                                        @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(service.addComment(postId,
                Long.parseLong(body.get("userId").toString()),
                (String) body.get("text")));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId,
                                           @RequestParam Long userId) {
        service.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }
}

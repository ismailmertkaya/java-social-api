package com.example.social.service;

import com.example.social.exception.ResourceNotFoundException;
import com.example.social.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class SocialService {

    private final Map<Long, User> users = new HashMap<>();
    private final Map<Long, Post> posts = new HashMap<>();
    private final AtomicLong userIdCounter = new AtomicLong(1);
    private final AtomicLong postIdCounter = new AtomicLong(1);

    public SocialService() {
      
        User u1 = createUser("hasan_dev", "hasan@dev.com", "Java & Python developer 🚀");
        User u2 = createUser("ayse_code", "ayse@dev.com", "Backend engineer | Coffee lover ☕");
        User u3 = createUser("tech_mehmet", "mehmet@tech.com", "CS student | Open source contributor");
       
        createPost(u1.getId(), "Just finished my Spring Boot project! #java #springboot 🎉");
        createPost(u2.getId(), "Python FastAPI is amazing for quick REST APIs #python #fastapi");
        createPost(u1.getId(), "Learning about data structures today #algorithms #cs");
        follow(u2.getId(), u1.getId());
        follow(u3.getId(), u1.getId());
    }

  

    public User createUser(String username, String email, String bio) {
        if (users.values().stream().anyMatch(u -> u.getUsername().equals(username)))
            throw new IllegalArgumentException("Username already taken: " + username);
        User user = new User(userIdCounter.getAndIncrement(), username, email, bio);
        users.put(user.getId(), user);
        return user;
    }

    public User getUser(Long id) {
        return Optional.ofNullable(users.get(id))
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    public List<User> getAllUsers() { return new ArrayList<>(users.values()); }

    public void follow(Long followerId, Long targetId) {
        User follower = getUser(followerId);
        User target = getUser(targetId);
        if (followerId.equals(targetId)) throw new IllegalArgumentException("Cannot follow yourself");
        follower.getFollowing().add(targetId);
        target.getFollowers().add(followerId);
    }

    public void unfollow(Long followerId, Long targetId) {
        User follower = getUser(followerId);
        User target = getUser(targetId);
        follower.getFollowing().remove(targetId);
        target.getFollowers().remove(followerId);
    }

   

    public Post createPost(Long authorId, String content) {
        User author = getUser(authorId);
        Post post = new Post(postIdCounter.getAndIncrement(), authorId, author.getUsername(), content);
        posts.put(post.getId(), post);
        return post;
    }

    public Post getPost(Long id) {
        return Optional.ofNullable(posts.get(id))
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found: " + id));
    }

    public List<Post> getAllPosts() {
        return posts.values().stream().filter(p -> !p.isDeleted())
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public List<Post> getPostsByUser(Long userId) {
        getUser(userId); 
        return posts.values().stream()
                .filter(p -> !p.isDeleted() && p.getAuthorId().equals(userId))
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

   
    public List<Post> getFeed(Long userId) {
        User user = getUser(userId);
        return posts.values().stream()
                .filter(p -> !p.isDeleted() && user.getFollowing().contains(p.getAuthorId()))
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public List<Post> searchByHashtag(String tag) {
        String normalized = tag.startsWith("#") ? tag.toLowerCase() : "#" + tag.toLowerCase();
        return posts.values().stream()
                .filter(p -> !p.isDeleted() && p.getHashtags().contains(normalized))
                .collect(Collectors.toList());
    }

    public Post likePost(Long postId, Long userId) {
        getUser(userId);
        Post post = getPost(postId);
        post.like(userId);
        return post;
    }

    public Post addComment(Long postId, Long userId, String text) {
        User user = getUser(userId);
        Post post = getPost(postId);
        post.addComment(new Comment(userId, user.getUsername(), text));
        return post;
    }

    public void deletePost(Long postId, Long requestingUserId) {
        Post post = getPost(postId);
        if (!post.getAuthorId().equals(requestingUserId))
            throw new IllegalStateException("You can only delete your own posts");
        post.setDeleted(true);
    }
}

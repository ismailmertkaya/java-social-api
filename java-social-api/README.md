# 📱 Social Media API — Spring Boot

A Twitter/Instagram-inspired social platform backend with users, posts, follows, likes, comments, and hashtag search.

## 🚀 Features
- User registration and profiles
- Follow / unfollow system
- Create posts (max 280 chars, like Twitter)
- Like and comment on posts
- Personalized feed (posts from followed users)
- Hashtag extraction and search
- Soft delete for posts

## 🛠️ Tech Stack
`Java 17` · `Spring Boot 3.2` · `Maven`

## ▶️ Run
```bash
./mvnw spring-boot:run
# API: http://localhost:8080
```

## 📡 API Endpoints

### Users
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | All users |
| GET | `/api/users/{id}` | User profile |
| POST | `/api/users` | Register user |
| POST | `/api/users/{id}/follow/{targetId}` | Follow a user |
| DELETE | `/api/users/{id}/follow/{targetId}` | Unfollow |

### Posts
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/posts` | All posts |
| GET | `/api/posts?hashtag=java` | Search by hashtag |
| GET | `/api/users/{id}/posts` | User's posts |
| GET | `/api/users/{id}/feed` | Personalized feed |
| POST | `/api/posts` | Create post |
| POST | `/api/posts/{id}/like` | Like a post |
| POST | `/api/posts/{id}/comments` | Comment on post |
| DELETE | `/api/posts/{id}?userId=X` | Delete own post |

## 🧪 Example: Create & Like a Post
```bash
# Create post
curl -X POST http://localhost:8080/api/posts \
  -H "Content-Type: application/json" \
  -d '{"authorId": 1, "content": "Hello world! #java #springboot"}'

# Like it
curl -X POST http://localhost:8080/api/posts/1/like \
  -H "Content-Type: application/json" \
  -d '{"userId": 2}'
```

## 📚 What I Learned
- Graph relationships (follow/follower) with HashSet
- Feed generation from social graph
- Regex-free hashtag parsing
- Soft delete pattern (mark as deleted vs. actually remove)
- Sorting streams by timestamp

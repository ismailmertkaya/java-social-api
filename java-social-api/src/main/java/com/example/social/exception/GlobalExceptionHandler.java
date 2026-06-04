package com.example.social.exception;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> notFound(ResourceNotFoundException ex) { return err(HttpStatus.NOT_FOUND, ex.getMessage()); }
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Map<String,Object>> badReq(RuntimeException ex) { return err(HttpStatus.BAD_REQUEST, ex.getMessage()); }
    private ResponseEntity<Map<String,Object>> err(HttpStatus s, String msg) {
        return ResponseEntity.status(s).body(Map.of("status", s.value(), "message", msg, "timestamp", LocalDateTime.now().toString()));
    }
}

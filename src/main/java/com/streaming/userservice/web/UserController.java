package com.streaming.userservice.web;

import com.streaming.userservice.dto.LoginRequest;
import com.streaming.userservice.dto.UserDTO;
import com.streaming.userservice.dto.VideoDTO;
import com.streaming.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest loginRequest) {
        UserDTO user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(user);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable Long id) {
        UserDTO user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }


    @PostMapping("/{userId}/watchlist/{videoId}")
    public ResponseEntity<Void> addToWatchlist(@PathVariable Long userId, @PathVariable Long videoId) {
        userService.addToWatchlist(userId, videoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/watchlist")
    public ResponseEntity<List<VideoDTO>> getUserWatchlist(@PathVariable Long userId) {
        // We return a List of VideoDTO so React can show the cards immediately
        List<VideoDTO> videos = userService.getWatchlist(userId);
        return ResponseEntity.ok(videos);
    }
    @DeleteMapping("/{userId}/watchlist/{videoId}")
    public ResponseEntity<Void> removeFromWatchlist(@PathVariable Long userId, @PathVariable Long videoId) {
        userService.removeFromWatchlist(userId, videoId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{userId}/history/{videoId}")
    public ResponseEntity<Void> addToHistory(@PathVariable Long userId, @PathVariable Long videoId) {
        userService.addToHistory(userId, videoId);
        return ResponseEntity.noContent().build();
    }
}
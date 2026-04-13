package com.streaming.userservice.services;

import com.streaming.userservice.dto.UserDTO;
import com.streaming.userservice.dto.VideoDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO login(String email, String password);
    UserDTO getUser(Long id);
    void addToWatchlist(Long userId, Long videoId);
    void addToHistory(Long userId, Long videoId);
    List<VideoDTO> getWatchlist(Long userId);
    void removeFromWatchlist(Long userId, Long videoId);
}
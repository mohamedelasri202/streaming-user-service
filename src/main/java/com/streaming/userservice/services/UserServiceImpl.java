package com.streaming.userservice.services;

import com.streaming.userservice.clients.VideoServiceClient;
import com.streaming.userservice.dto.UserDTO;
import com.streaming.userservice.dto.VideoDTO;
import com.streaming.userservice.entities.User;
import com.streaming.userservice.mappers.UserMapper;
import com.streaming.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final VideoServiceClient videoServiceClient;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.fromDTO(userDTO);
        // Encode password before saving to DB
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserDTO login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));


        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }

        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }

    @Override
    public void addToWatchlist(Long userId, Long videoId) {

        if (!videoServiceClient.checkVideoExists(videoId)) {
            throw new RuntimeException("Video ID " + videoId + " does not exist!");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getWatchlist().add(videoId);

    }

    @Override
    public List<VideoDTO> getWatchlist(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<Long> videoIds = user.getWatchlist();

        if (videoIds.isEmpty()) {
            return new ArrayList<>();
        }


        return videoServiceClient.getVideosBatch(new ArrayList<>(videoIds));
    }

    @Override
    public void addToHistory(Long userId, Long videoId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getHistory().add(videoId);

    }

    @Override
    public void removeFromWatchlist(Long userId, Long videoId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        user.getWatchlist().remove(videoId);


    }
}
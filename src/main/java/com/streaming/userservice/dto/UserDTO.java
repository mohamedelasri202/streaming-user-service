package com.streaming.userservice.dto;

import lombok.*;
import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserDTO {
    private Long id;
    private String password;
    private String username;
    private String email;
    private Set<Long> watchlist;
    private Set<Long> history;
}
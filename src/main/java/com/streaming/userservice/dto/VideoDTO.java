package com.streaming.userservice.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VideoDTO {
    private Long id;
    private String title;
    private String description;
    private String url;
    private String thumbnailUrl;
    private String duration;
    private Integer releaseYear;
    private Double rating;
    private String director;
    private String cast;
    private String categoryName;
}
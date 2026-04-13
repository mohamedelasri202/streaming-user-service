package com.streaming.userservice.clients;

import  com.streaming.userservice.dto.VideoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@FeignClient(name = "video-service")
public interface VideoServiceClient {

    @GetMapping("/api/videos/exists/{id}")
    boolean checkVideoExists(@PathVariable("id") Long id);

    // Add this to get the actual data for the watchlist
    @PostMapping("/api/videos/batch")
    List<VideoDTO> getVideosBatch(@RequestBody List<Long> ids);
}
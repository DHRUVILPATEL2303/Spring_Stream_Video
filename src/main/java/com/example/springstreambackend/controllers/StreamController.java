package com.example.springstreambackend.controllers;

import com.example.springstreambackend.entities.VideoModel;
import com.example.springstreambackend.services.IVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/video")
public class StreamController {
    private final IVideoService videoService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public String uploadVideo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("contentType") String contentType

    ) {
        VideoModel videoModel = VideoModel.builder()

                .title(title)
                .description(description)
                .contentType(contentType)

                .build();

        return videoService.uploadVideo(videoModel, file);
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadVideo(@PathVariable String id) {
        return videoService.downloadVideoById(id);
    }
}

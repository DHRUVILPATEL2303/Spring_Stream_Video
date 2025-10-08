package com.example.springstreambackend.controllers;

import com.example.springstreambackend.entities.VideoModel;
import com.example.springstreambackend.services.IVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
        return videoService.uploadVideo(
                VideoModel.builder()
                        .title(title)
                        .description(description)
                        .contentType(contentType)
                        .build(),
                file
        );
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadVideo(@PathVariable String id) {
        return videoService.downloadVideoById(id);
    }

    @GetMapping("/stream/range/{id}")
    public ResponseEntity<Resource> streamVideoRange(
            @PathVariable String id,
            @RequestHeader(value = "Range", required = false) String range
    ) {
        return videoService.streamVideoInRange(id, range);
    }

    @GetMapping("/all")
    public List<VideoModel> getAllVideos() {
        return videoService.getAllVideos();
    }

    @GetMapping("/{id}")
    public VideoModel getVideoById(@PathVariable String id) {
        return videoService.getVideoById(id);
    }
}
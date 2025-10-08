package com.example.springstreambackend.services;

import com.example.springstreambackend.entities.VideoModel;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IVideoService {

    String uploadVideo(VideoModel videoModel, MultipartFile file);

    VideoModel getVideoById(String id);

    VideoModel getVideoByName(String name);

    List<VideoModel> getAllVideos();

    ResponseEntity<Resource> downloadVideoById(String id);
}

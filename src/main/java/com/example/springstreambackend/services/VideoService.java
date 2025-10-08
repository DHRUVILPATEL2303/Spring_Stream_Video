package com.example.springstreambackend.services;

import com.example.springstreambackend.entities.VideoModel;
import com.example.springstreambackend.repository.VideoStreamRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.support.WindowIterator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class VideoService implements IVideoService {
    private final VideoStreamRepository videoStreamRepository;

    @Value("${files.video}")
    String DIR;

    @PostConstruct
    public void init() {
        File file = new File(DIR);
        if (!file.exists()) {
            file.mkdir();
        } else {
            System.out.println("Director already Created");
        }
    }

    @Override
    public String uploadVideo(VideoModel videoModel, MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String contentType = file.getContentType();

            InputStream inputStream = file.getInputStream();

            String cleanedFileName = StringUtils.cleanPath(fileName);
            String cleanedFolderPAth = StringUtils.cleanPath(DIR);

            Path path = Paths.get(cleanedFolderPAth, cleanedFileName);
            System.out.println(path);

            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            videoModel.setContentType(contentType);
            videoModel.setFilePath(path.toString());

            videoStreamRepository.save(videoModel);
            return path.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public VideoModel getVideoById(String id) {
        return videoStreamRepository.findById(id).orElseThrow(() -> new RuntimeException("Video Not Found"));
    }

    @Override
    public VideoModel getVideoByName(String name) {
        return null;
    }

    @Override
    public ResponseEntity<Resource> downloadVideoById(String id) {
        VideoModel video = getVideoById(id);
        File file = new File(video.getFilePath());

        if (!file.exists()) {
            throw new RuntimeException("File not found");
        }

        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, video.getContentType())
                .body(resource);
    }

    @Override
    public List<VideoModel> getAllVideos() {
        return List.of();
    }
}

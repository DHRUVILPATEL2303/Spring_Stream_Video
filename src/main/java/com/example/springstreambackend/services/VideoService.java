package com.example.springstreambackend.services;

import com.example.springstreambackend.entities.VideoModel;
import com.example.springstreambackend.repository.VideoStreamRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


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
    public ResponseEntity<Resource> streamVideoInRange(String id, String rangeHeader) {
        VideoModel video = getVideoById(id);
        File file = new File(video.getFilePath());
        if (!file.exists()) throw new RuntimeException("File not found");

        String contentType = video.getContentType() != null ? video.getContentType() : "application/octet-stream";
        long fileLength = file.length();

        if (rangeHeader == null) {
            Resource resource = new FileSystemResource(file);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(resource);
        }

        try {
            String[] ranges = rangeHeader.replace("bytes=", "").split("-");
            long rangeStart = Long.parseLong(ranges[0]);
            long rangeEnd = ranges.length > 1 && !ranges[1].isEmpty() ? Long.parseLong(ranges[1]) : fileLength - 1;
            if (rangeEnd > fileLength - 1) rangeEnd = fileLength - 1;

            long contentLength = rangeEnd - rangeStart + 1;

            InputStream inputStream = Files.newInputStream(file.toPath());
            inputStream.skip(rangeStart);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentLength(contentLength);
            headers.add("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + fileLength);
            headers.add("Accept-Ranges", "bytes");
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("Last-Modified", String.valueOf(file.lastModified()));

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .headers(headers)
                    .body(new InputStreamResource(inputStream));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @Override
    public List<VideoModel> getAllVideos() {
        return List.of();
    }
}

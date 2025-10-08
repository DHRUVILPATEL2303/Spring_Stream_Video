package com.example.springstreambackend.entities;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "video_stream")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String videoId;


    private String description;

    private String title;

    private String contentType;

    private String filePath;


}

package com.example.springstreambackend.repository;

import com.example.springstreambackend.entities.VideoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoStreamRepository extends JpaRepository<VideoModel,String> {

}

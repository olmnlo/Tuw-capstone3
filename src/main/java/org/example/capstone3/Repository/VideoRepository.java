package org.example.capstone3.Repository;

import org.example.capstone3.Model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    Video findVideoById(Integer id);
}

package org.example.capstone3.Repository;

import org.example.capstone3.Model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


//Hussam created
@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    Video findVideoById(Integer id);

    @Query("SELECT v FROM Video v WHERE v.plan.id = :planId AND v.plan.doctor.id = :doctorId AND LOWER(v.videoType) = LOWER(:videoType)")
    List<Video> findVideosByPlanAndDoctorAndType(@Param("planId") Integer planId,
                                                 @Param("doctorId") Integer doctorId,
                                                 @Param("videoType") String videoType);
}

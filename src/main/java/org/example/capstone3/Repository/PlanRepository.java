package org.example.capstone3.Repository;

import org.example.capstone3.Model.Plan;
import org.example.capstone3.Model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//Mohammed
@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {

    Plan findPlanById(Integer id);
//Hussam fixed
    boolean existsPlanByDoctor_IdAndPatient_Id(Integer doctorId, Integer patientId);

    @Query("SELECT v FROM Plan p JOIN p.video v WHERE p.id = :id AND p.doctor.id = :doctorId")
    List<Video> findVideosByPlanIdAndDoctorId(@Param("id") Integer id, @Param("doctorId") Integer doctorId);

    Plan findPlanByIdAndPatient_Id(Integer id, Integer patientId);

    Plan findPlanByIdAndDoctor_Id(Integer id, Integer doctorId);
}

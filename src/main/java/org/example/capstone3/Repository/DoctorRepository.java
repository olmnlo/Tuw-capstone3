package org.example.capstone3.Repository;

import org.example.capstone3.Model.Doctor;
import org.example.capstone3.Model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
//Aziz
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor findDoctorById(Integer id);

    List<Doctor> findDoctorByNameContains(String name);

}

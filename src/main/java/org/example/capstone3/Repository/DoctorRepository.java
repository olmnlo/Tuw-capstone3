package org.example.capstone3.Repository;

import org.example.capstone3.Model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//Aziz
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor findDoctorById(Integer id);
}

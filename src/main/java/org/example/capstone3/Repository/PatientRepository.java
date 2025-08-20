package org.example.capstone3.Repository;

import org.example.capstone3.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//Hussam created
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Patient findPatientById(Integer id);
}

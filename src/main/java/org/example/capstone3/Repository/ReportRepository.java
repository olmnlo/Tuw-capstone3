package org.example.capstone3.Repository;

import org.example.capstone3.Model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
//Aziz
public interface ReportRepository extends JpaRepository<Report, Integer> {
    Report findReportById(Integer id);
    List<Report> findByPatientId(Integer patientId);
//Hussam fixed
    Report findReportByPatient_IdAndDoctor_Id(Integer patientId, Integer doctorId);
}

package org.example.capstone3.Repository;

import org.example.capstone3.Model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    Report findReportById(Integer id);
}

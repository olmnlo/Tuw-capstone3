package org.example.capstone3.Repository;

import org.example.capstone3.Model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
//Hussam created
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    Answer getAnswerByPatient_Id(Integer patientId);

    List<Answer> getAnswersByPatient_Id(Integer patientId);
}

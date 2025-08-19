package org.example.capstone3.Repository;

import org.example.capstone3.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
//Mohammed
@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {

    Question findQuestionById(Integer id);

}

package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Patient;
import org.example.capstone3.Model.Question;
import org.example.capstone3.Repository.PatientRepository;
import org.example.capstone3.Repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
//Mohammed
@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final PatientRepository patientRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public void addQuestion(Question question) {
        questionRepository.save(question);
    }

    public void updateQuestion(Integer questionId, Question question) {
        Question question1 = questionRepository.findQuestionById(questionId);
        if (question1 == null) {
            throw new ApiException("Question not found");
        }
        question1.setQuestion(question.getQuestion());
        question1.setAnswer(question.getAnswer());
        questionRepository.save(question1);
    }

    public void deleteQuestion(Integer questionId) {
        Question question1 = questionRepository.findQuestionById(questionId);
        if (question1 == null) {
            throw new ApiException("Question not found");
        }
        questionRepository.delete(question1);
    }

    public void assignQuestionToPatient(Integer patientId) {
        Patient patient = patientRepository.findPatientById(patientId);
        if (patient == null) {
            throw new ApiException("Question or Plan not found");
        }
        Question question=null;
        if(patient.getBooking().getStatus().toLowerCase().equalsIgnoreCase("go to plan"))
        question=new Question(patientId,"","",patient);


    }
}

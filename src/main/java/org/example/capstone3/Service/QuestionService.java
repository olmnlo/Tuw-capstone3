package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.DTOin.QuestionDTO;
import org.example.capstone3.Model.Question;
import org.example.capstone3.Repository.PatientRepository;
import org.example.capstone3.Repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
//Mohammed
@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;


    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
//Hussam : add
    public void addQuestion(QuestionDTO questionDTO) {
        Question question = new Question(null, questionDTO.getQuestion());
        questionRepository.save(question);
    }

    //Hussam fix
    public void updateQuestion(Integer questionId, QuestionDTO questionDTO) {
        Question question1 = questionRepository.findQuestionById(questionId);
        if (question1 == null) {
            throw new ApiException("Question not found");
        }
        //Hussam fix
        question1.setQuestion(questionDTO.getQuestion());
        questionRepository.save(question1);
    }

    public void deleteQuestion(Integer questionId) {
        Question question1 = questionRepository.findQuestionById(questionId);
        if (question1 == null) {
            throw new ApiException("Question not found");
        }
        questionRepository.delete(question1);
    }

//    public void assignQuestionToPatient(Integer patientId) {
//        Patient patient = patientRepository.findPatientById(patientId);
//        if (patient == null) {
//            throw new ApiException("Question or Plan not found");
//        }
//        for (Question question : questionRepository.findAll()){
//            question.setPatient(patient);
//            questionRepository.save(question);
//        }


    }

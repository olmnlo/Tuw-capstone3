package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.DTOin.AnswerDTO;
import org.example.capstone3.Model.Answer;
import org.example.capstone3.Model.Patient;
import org.example.capstone3.Model.Question;
import org.example.capstone3.Repository.AnswerRepository;
import org.example.capstone3.Repository.PatientRepository;
import org.example.capstone3.Repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
//Hussam created
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final PatientRepository patientRepository;
    private final QuestionRepository questionRepository;


    public void assignQuestionsToPatient(Integer patientId) {
        Patient patient = patientRepository.findPatientById(patientId);
        if (patient == null) {
            throw new ApiException("Patient not found");
        }

        List<Question> allQuestions = questionRepository.findAll();

        for (Question q : allQuestions) {
            Answer answer = new Answer();
            answer.setPatient(patient);
            answer.setQuestion(q);
            answer.setAnswer(""); // empty initially
            answerRepository.save(answer);
        }
    }


    public void answerTheQuestion(Integer patient_id, Integer question_id ,AnswerDTO answerDTO){
        Patient patient = patientRepository.findPatientById(patient_id);
        if (patient == null){
            throw new ApiException("patient not found");
        }
        Question question = questionRepository.findQuestionById(question_id);
        if (question == null){
            throw new ApiException("question not found");
        }
        List<Answer> answer = answerRepository.getAnswersByPatient_Id(patient_id);
        answer.get(question_id-1).setAnswer(answerDTO.getAnswer());
        answerRepository.save(answer.get(question_id-1));
    }
}

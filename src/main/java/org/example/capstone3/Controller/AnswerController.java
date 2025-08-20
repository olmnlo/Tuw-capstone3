package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.DTOin.AnswerDTO;
import org.example.capstone3.Service.AnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//Hussam created
@RestController
@RequestMapping("/api/v1/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;

    //ENDPOINT 1
    @PutMapping("assign/patient/{patient_id}")
    public ResponseEntity<ApiResponse> assignQuestionToPatient(@PathVariable Integer patient_id) {
        answerService.assignQuestionsToPatient(patient_id);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully assigned questions to patient"));
    }

    //ENDPOINT 2
    @PostMapping("/question/{question_id}/patient/{patient_id}/answer")
    public ResponseEntity<ApiResponse> answerQuestion(@PathVariable Integer patient_id,@PathVariable Integer question_id ,@Valid@RequestBody AnswerDTO answerDTO){
        answerService.answerTheQuestion(patient_id, question_id, answerDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("answered successfully"));
    }
}

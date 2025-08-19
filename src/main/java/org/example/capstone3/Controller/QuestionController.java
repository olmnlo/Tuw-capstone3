package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.Plan;
import org.example.capstone3.Model.Question;
import org.example.capstone3.Service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//Hussam: general fix
//Mohammed
@RestController
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<?> getAllQuestions(){
        return ResponseEntity.ok().body(questionService.getAllQuestions());
    }


    //Hussam: fixed make it clear and small letter
    @PostMapping
    public ResponseEntity<ApiResponse> addQuestion(@RequestBody @Valid Question question){
        questionService.addQuestion(question);
        return ResponseEntity.ok().body(new ApiResponse("Question added successfully"));
    }
    //Hussam: fixed make it clear and small letter
    @PutMapping("/{question_id}")
    public ResponseEntity<ApiResponse> updateQuestion(@PathVariable Integer question_id,@RequestBody @Valid Question question){
        questionService.updateQuestion(question_id, question);
        return ResponseEntity.ok().body(new ApiResponse("Question updated successfully"));
    }
    //Hussam: fixed make it clear and small letter
    @DeleteMapping("/{question_id}")
    public ResponseEntity<ApiResponse> deleteQuestion(@PathVariable Integer question_id){
        questionService.deleteQuestion(question_id);
        return ResponseEntity.ok().body(new ApiResponse("Question deleted successfully"));
    }
    @PutMapping("assign/patient/{patient_id}")
    public ResponseEntity<ApiResponse> assignQuestionToPatient(@PathVariable Integer patient_id) {
        questionService.assignQuestionToPatient(patient_id);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully assigned plan to patient"));
    }


}

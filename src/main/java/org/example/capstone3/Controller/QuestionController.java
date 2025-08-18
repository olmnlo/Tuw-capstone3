package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.Plan;
import org.example.capstone3.Model.Question;
import org.example.capstone3.Service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Mohammed
@RestController
@RequestMapping("/api/v1/QUESTION")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<?> getAllQuestions(){
        return ResponseEntity.ok().body(questionService.getAllQuestions());
    }


    @PostMapping("/ADD")
    public ResponseEntity<?> addQuestion(@RequestBody @Valid Question question){
        questionService.addQuestion(question);
        return ResponseEntity.ok().body(new ApiResponse("Question added successfully"));
    }
    @PutMapping("/UPDATE/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable Integer id,@RequestBody @Valid Question question){
        questionService.updateQuestion(id, question);
        return ResponseEntity.ok().body(new ApiResponse("Question updated successfully"));
    }
    @DeleteMapping("/DELETE/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Integer id){
        questionService.deleteQuestion(id);
        return ResponseEntity.ok().body(new ApiResponse("Question deleted successfully"));
    }
    @PutMapping("/ASSIGN/Question/To/Patient/{patientId}")
    public ResponseEntity<?> assignQuestionToPatient(@PathVariable Integer patientId) {
        questionService.assignQuestionToPatient(patientId);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully assigned plan to patient"));
    }


}

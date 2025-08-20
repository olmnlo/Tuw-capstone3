package org.example.capstone3.DTOin;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
//Hussam created
@AllArgsConstructor
@Setter
@Getter
public class QuestionDTO {
    @NotEmpty(message = "question is required")
    private String question;
}

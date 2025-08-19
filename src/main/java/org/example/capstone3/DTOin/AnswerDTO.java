package org.example.capstone3.DTOin;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//Hussam created
@AllArgsConstructor
@Setter
@Getter
public class AnswerDTO {
    @NotEmpty(message = "answer is required")
    private String answer;
}

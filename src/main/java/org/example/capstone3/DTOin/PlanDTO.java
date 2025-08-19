package org.example.capstone3.DTOin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.capstone3.Model.Doctor;
import org.example.capstone3.Model.Patient;
import org.example.capstone3.Model.Video;

import java.util.Set;

//Hussam created
@Setter
@Getter
@AllArgsConstructor
public class PlanDTO {
    @Id
    private Integer id;

    @NotEmpty(message = "name is required")
    @Size(max = 30, message = "name max length 30")
    private String name;

    @NotEmpty(message = "description is required")
    private String description;
}

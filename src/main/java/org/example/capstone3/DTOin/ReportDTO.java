package org.example.capstone3.DTOin;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
//Hussam created
@Getter
@Setter
@AllArgsConstructor
public class ReportDTO {

    @NotEmpty(message = "description can't be null")
    private String description;

    @NotNull(message = "appointment date is required")
    @FutureOrPresent(message = "appointment date must be in future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportDate;

    @NotNull(message = "doctor id is required")
    private Integer doctorId;
    @NotNull(message = "patient id is required")
    private Integer patientId;
}

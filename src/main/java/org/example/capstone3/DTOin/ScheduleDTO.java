package org.example.capstone3.DTOin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.capstone3.Model.Doctor;

import java.time.LocalDate;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class ScheduleDTO {

    @NotNull(message = "Date can't be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "time can't be null")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime time;

    @NotNull(message = "doctor id must not be null")
    @Positive(message = "doctor id must be positive")
    private Integer doctor;
}

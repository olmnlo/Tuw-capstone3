package org.example.capstone3.DTOin;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
//Hussam fixed
//Mohammed
@Setter
@Getter
@AllArgsConstructor
public class BookingDTO {

    @NotNull(message = "appointment date is required")
    @FutureOrPresent(message = "appointment date must be in future")
    @JsonFormat(pattern = "yyyy-MM-dd 'T' HH:mm:ss")
    private LocalDateTime appointmentDate;

    @NotNull(message = "appointment day is required")
    @FutureOrPresent(message = "appointment date must be in future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDay;
}

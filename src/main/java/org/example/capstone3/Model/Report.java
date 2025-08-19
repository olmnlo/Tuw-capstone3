package org.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;


@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
//Aziz
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "description can't be null")
    @Column(columnDefinition = "varchar(500) not null")
    private String description;

    @NotNull(message = "appointment date is required")
    @FutureOrPresent(message = "appointment date must be in future")
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    @Column(columnDefinition = "TIMESTAMP not null")
    private LocalDate reportDate;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;


}

package org.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

//Hussam fixed
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

    @Column(columnDefinition = "text not null")
    private String description;

    @Column(columnDefinition = "TIMESTAMP not null")
    private LocalDate reportDate;

    @ManyToOne
    @JsonIgnore
    private Patient patient;

    @ManyToOne
    @JsonIgnore
    private Doctor doctor;


}

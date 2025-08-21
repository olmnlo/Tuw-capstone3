package org.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(columnDefinition = "TIMESTAMP not null")
    @JsonFormat(pattern = "yyyy-MM-dd 'T' HH:mm")
    private LocalDateTime appointmentDate;


    @Column(columnDefinition = "text")
    private String description = "";

    @Column(columnDefinition = "varchar(15) not null")
    private String status = "wait";


    @ManyToOne
    @JsonIgnore
    private Patient patient;

    @ManyToOne
    @JsonIgnore
    private Doctor doctor;

    @ManyToOne
    @JsonIgnore
    private Schedule schedule;

}

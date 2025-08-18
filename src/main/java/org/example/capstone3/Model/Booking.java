package org.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {
    //This class Mohammed
    @Id
    private Integer id;

    private String description;

    private LocalDateTime appointmentDate;

    private LocalDate appointmentDay;

    private Boolean status;



    @OneToOne
    @MapsId
    @JsonIgnore
    private Patient patient;


    @ManyToOne
    @JsonIgnore
    private Doctor doctor;



}

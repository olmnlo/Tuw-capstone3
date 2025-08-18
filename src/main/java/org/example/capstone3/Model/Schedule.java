package org.example.capstone3.Model;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;


public class Schedule {

    private Integer id;

    private LocalDate date;

    @ManyToOne

    private Doctor doctor;
}

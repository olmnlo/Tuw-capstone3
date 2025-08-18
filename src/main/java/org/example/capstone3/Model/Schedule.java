package org.example.capstone3.Model;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Schedule {

    private Integer id;

    private LocalDate date;
    @ManyToOne
    private Doctor doctor;
}

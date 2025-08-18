package org.example.capstone3.Model;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class Schedule {

    @Id
    private Integer id;
    @ManyToOne
    private Doctor doctor;
}

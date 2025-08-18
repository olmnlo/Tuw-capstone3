package org.example.capstone3.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Report {

    private Integer id;

    private String description;

    private Doctor doctor;

    private Patient patient;
}

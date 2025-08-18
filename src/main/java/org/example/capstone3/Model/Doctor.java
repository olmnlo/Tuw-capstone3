package org.example.capstone3.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Data
//Hussam
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String specialization;

    private String name;
    private Integer age;
    private Character sex;
    private String bio;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor")
    private Set<Schedule> schedule;

    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile;

}

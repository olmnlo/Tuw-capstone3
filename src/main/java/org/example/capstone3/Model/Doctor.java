package org.example.capstone3.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.Setter;

import java.util.Set;
//Hussam fixed
@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
//Hussam
 
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(columnDefinition = "varchar(20) unique not null")
    private String username;


    @Column(columnDefinition = "varchar(20) not null")
    private String password;


    @Column(columnDefinition = "varchar(30) not null")
    private String name;

    @Column(columnDefinition = "int not null")
    private Integer age;

    @Column(columnDefinition = "varchar(6) not null")
    private String sex;


    //Mohammed Add
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor")
    private Set<Schedule> schedule;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "doctor")
    private Set<Booking> booking;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor")
    @JsonIgnore
    private Set<Report> report;


}

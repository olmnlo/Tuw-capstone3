package org.example.capstone3.Model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.Setter;

import java.util.Set;
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

    private String username;

    private String password;

    private String specialization;

    private String name;
    private Integer age;
    private Character sex;
    private String bio;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
    private Set<Schedule> schedule;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "doctor")
    private Set<Booking> booking;


}

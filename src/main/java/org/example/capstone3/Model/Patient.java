package org.example.capstone3.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Patient {

    @Id
    private Integer id;


    private String username;



    @OneToOne(cascade = CascadeType.ALL, mappedBy = "Patient")
    @PrimaryKeyJoinColumn
    private Booking booking;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "patient")
    private Set<Question> question;



}

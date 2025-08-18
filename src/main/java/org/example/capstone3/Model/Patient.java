package org.example.capstone3.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
//Hussam
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(columnDefinition = "varchar(30) not null")
    private String name;

    @Column(columnDefinition = "varchar(20) unique not null")
    private String username;

    @Column(columnDefinition = "varchar(20) not null")
    private String password;

    @Column(columnDefinition = "int not null")
    private Integer age;


    @Column(columnDefinition = "varchar(6) not null")
    private String sex;


    @OneToOne(cascade = CascadeType.ALL, mappedBy = "patient")
    private Booking booking;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "patient")
    private Plan plan;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "patient")
    private Report report;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "patient")
    private Set<Question> question;



}

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

    @NotEmpty(message = "username is required")
    @Size(max = 20, message = "username max length 20")
    @Column(columnDefinition = "varchar(20) unique not null")
    private String username;

    @NotEmpty(message = "password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?`~])(?=.{8,}).*$", message = "password must has 1 lower/upper 1 digit 1 special character")
    @Size(max = 20, message = "password max length 20")
    @Column(columnDefinition = "varchar(20) not null")
    private String password;

    @NotEmpty(message = "name is required")
    @Size(max = 30, message = "name max length 30")
    @Column(columnDefinition = "varchar(30) not null")
    private String name;

    @NotNull(message = "age is required")
    @Positive(message = "age must be positive")
    @Min(value = 16, message = "your age must be more than 16")
    private Integer age;

    @NotEmpty(message = "sex is required")
    @Size(max = 6, message = "sex max length 6")
    @Pattern(regexp = "^(male|female)$", message = "sex must be either male or female")
    private String sex;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor")
    private Set<Schedule> schedule;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "doctor")
    @JsonIgnore
    private Set<Booking> booking;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor")
    @JsonIgnore
    private Set<Report> report;


}

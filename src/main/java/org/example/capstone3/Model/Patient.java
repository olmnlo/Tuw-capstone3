package org.example.capstone3.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
//Hussam
public class Patient {

    private Integer id;

    private String username;
    private String password;
    private String status;
    private Double balance;
    private String name;
    private Integer age;
    private Character sex;
    private String bio;



}

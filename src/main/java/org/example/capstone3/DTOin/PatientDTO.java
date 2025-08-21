package org.example.capstone3.DTOin;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
//Hussam created
@Getter
@Setter
@AllArgsConstructor
public class PatientDTO {

    @NotEmpty(message = "name is required")
    @Size(max = 30, message = "name max length 30")
    @Column(columnDefinition = "varchar(30) not null")
    private String name;

    @NotEmpty(message = "username is required")
    @Size(max = 20, message = "username max length 20")
    @Column(columnDefinition = "varchar(20) unique not null")
    private String username;

    @NotEmpty(message = "password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?`~])(?=.{8,}).*$", message = "password must has 1 lower/upper 1 digit 1 special character")
    @Size(max = 20, message = "password max length 20")
    @Column(columnDefinition = "varchar(20) not null")
    private String password;

    @NotNull(message = "age is required")
    @Positive(message = "age must be positive")
    @Min(value = 16, message = "your age must be more than 16")
    private Integer age;

    @NotEmpty(message = "sex is required")
    @Size(max = 6, message = "sex max length 6")
    @Pattern(regexp = "^(male|female)$", message = "sex must be either male or female")
    private String sex;


    //Mohammed Add @Email
    //Hussam fix add @NotEmpty, Size
    @NotEmpty(message = "email is required")
    @Size(max = 30, message = "max length for email is 30")
    @Email(message = "Email must be in valid form")
    private String email;
}

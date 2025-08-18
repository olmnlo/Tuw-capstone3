package org.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
//Mohammed
@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Booking {

    @Id
    private Integer id;

    @NotNull(message = "appointment date is required")
    @FutureOrPresent(message = "appointment date must be in future")
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    @Column(columnDefinition = "TIMESTAMP not null")
    private LocalDateTime appointmentDate;

    @NotNull(message = "appointment day is required")
    @FutureOrPresent(message = "appointment date must be in future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private LocalDate appointmentDay;

    @Column(columnDefinition = "text")
    private String description = "";

    @Column(columnDefinition = "varchar(15) not null")
    private String status = "waiting";


    @OneToOne
    @JsonIgnore
    @MapsId
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

}

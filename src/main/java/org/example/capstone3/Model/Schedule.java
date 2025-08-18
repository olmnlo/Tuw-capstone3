package org.example.capstone3.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;



import java.time.LocalDate;


@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
//Aziz
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Date can't be null")
    @Column(columnDefinition = "date not null")
    private LocalDate date;

    @ManyToOne
    @JsonIgnore
    private Doctor doctor;

}

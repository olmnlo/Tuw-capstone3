package org.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Plan {

    private Integer id;

    private String name;

    private String description;

    @ManyToOne
    @JsonIgnore
    private Patient patient;


}

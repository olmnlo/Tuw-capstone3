package org.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
//Hussam fixed
@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Plan {

    @Id
    private Integer id;

    private String name;

    private String description;

    @OneToOne
    @JsonIgnore
    @MapsId
    private Patient patient;

    @ManyToOne
    @JsonIgnore
    private Doctor doctor;



    //Mohammed: add to list first for doctor and second for doctor
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plan")
    @JsonIgnore
    private List<Video> video;


}

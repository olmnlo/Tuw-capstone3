package org.example.capstone3.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Doctor {
    @Id
    private Integer id;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "doctor")
    private Set<Booking> booking;



}

package org.example.capstone3.DTOout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.capstone3.Model.Schedule;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class DoctorDTOout {
    
    private String name;
    private String sex;
    private Set<Schedule> schedules;

}

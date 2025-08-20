package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.DTOin.ScheduleDTO;
import org.example.capstone3.Model.Doctor;
import org.example.capstone3.Model.Schedule;
import org.example.capstone3.Repository.DoctorRepository;
import org.example.capstone3.Repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
//Aziz
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;

    public List<Schedule> findAllSchedules(){
        List<Schedule> schedules = scheduleRepository.findAll();
        if (schedules.isEmpty()){
            throw new ApiException("no schedule found !");
        }
        return schedules;
    }


    public void addSchedule(ScheduleDTO scheduleDTO){
        Doctor doctor = doctorRepository.findDoctorById(scheduleDTO.getDoctor());
        if (doctor == null){
            throw new ApiException("doctor not found");
        }


        // reject if same doctor already has a slot at this exact date+time
        boolean conflict = scheduleRepository.existsByDoctor_IdAndDateAndTimeBetween(
                scheduleDTO.getDoctor(),
                scheduleDTO.getDate(),
                scheduleDTO.getTime().minusMinutes(9), // prevent overlap within 10 minutes
                scheduleDTO.getTime().plusMinutes(9)
        );

        if (conflict) {
            throw new ApiException("Doctor already has a slot within 10 minutes");
        }

        Schedule schedule = new Schedule(
                null,
                scheduleDTO.getDate(),
                scheduleDTO.getTime(),
                doctor
        );
        scheduleRepository.save(schedule);
    }

    public void updateSchedule(Integer id, ScheduleDTO scheduleDTO){
        Schedule oldSchedule = scheduleRepository.findScheduleById(id);
        if (oldSchedule == null){
            throw new ApiException("no schedule found !");
        }
        Doctor doctor = doctorRepository.findDoctorById(scheduleDTO.getDoctor());
        if (oldSchedule.getDoctor().equals(doctor)){
            throw new ApiException("doctor not found");
        }
        oldSchedule.setDate(scheduleDTO.getDate());
        scheduleRepository.save(oldSchedule);
    }

    public void deleteSchedule(Integer id){
        Schedule schedule = scheduleRepository.findScheduleById(id);

        if (schedule == null){
            throw new ApiException("no schedule found !");
        }

        scheduleRepository.delete(schedule);
    }




}
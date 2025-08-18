package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Schedule;
import org.example.capstone3.Repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
//Aziz
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public List<Schedule> findAllSchedules(){
        List<Schedule> schedules = scheduleRepository.findAll();
        if (schedules.isEmpty()){
            throw new ApiException("no schedule found !");
        }
        return schedules;
    }


    public void addSchedule(Schedule schedule){
        scheduleRepository.save(schedule);
    }

    public void updateSchedule(Integer id, Schedule schedule){
        Schedule oldSchedule = scheduleRepository.findScheduleById(id);

        if (oldSchedule == null){
            throw new ApiException("no schedule found !");
        }


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
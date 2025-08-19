package org.example.capstone3.Repository;

import org.example.capstone3.Model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//Aziz
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    Schedule findScheduleById(Integer id);
}

package org.example.capstone3.Repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.example.capstone3.Model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.List;


@Repository
//Aziz
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    Schedule findScheduleById(Integer id);


    boolean existsByDateAndTimeAndDoctor_Id(LocalDate localDate, LocalTime localTime, Integer doctorId);

    List<Schedule> findByDoctor_Id(Integer doctorId);

    boolean existsByDoctor_IdAndDateAndTimeBetween(@NotNull(message = "doctor id must not be null") @Positive(message = "doctor id must be positive") Integer doctor, @NotNull(message = "Date can't be null") LocalDate date, LocalTime localTime, LocalTime localTime1);

    @Query("select s from Schedule s " +
            "where (s.date > :today) or (s.date = :today and s.time > :now) " +
            "order by s.date asc, s.time asc")
    List<Schedule> findUpcomingSchedules(@Param("today") LocalDate today,
                                         @Param("now") LocalTime now);

    Schedule findByDateAndTimeAndDoctor_Id(LocalDate localDate, LocalTime localTime, Integer doctorId);
}

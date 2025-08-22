package org.example.capstone3.Repository;

import org.example.capstone3.Model.Booking;
import org.example.capstone3.Model.Schedule;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

//Mohammed
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Booking getBookingById(Integer id);

    //Mohammed
    List<Booking> findByPatient_Id(Integer patientId);

    List<Booking> findBookingByDoctor_Id(Integer doctorId);

    Booking findBookingByPatient_Id(Integer patientId);

    Booking findBookingByPatient_IdOrderByAppointmentDateDesc(Integer patientId, Limit limit);


    boolean existsByDoctor_IdAndAppointmentDate(Integer doctorId, LocalDateTime of);

    boolean existsByDoctor_IdAndAppointmentDateAfter(Integer doctorId, LocalDateTime appointmentDateAfter);

    boolean existsBySchedule(Schedule schedule);

    List<Booking> findBookingByAppointmentDateBetween(LocalDateTime appointmentDateAfter, LocalDateTime appointmentDateBefore);
}

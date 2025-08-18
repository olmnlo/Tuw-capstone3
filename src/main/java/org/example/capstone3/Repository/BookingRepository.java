package org.example.capstone3.Repository;

import org.example.capstone3.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//Mohammed
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Booking getBookingById(Integer id);
}

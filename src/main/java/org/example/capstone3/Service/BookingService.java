package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Booking;
import org.example.capstone3.Model.Doctor;
import org.example.capstone3.Model.Patient;
import org.example.capstone3.Repository.BookingRepository;
import org.example.capstone3.Repository.DoctorRepository;
import org.example.capstone3.Repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//Mohammed
@Service
@RequiredArgsConstructor
public class BookingService {
    private BookingRepository bookingRepository;
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;



    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public void addBooking(Integer patientId,Integer doctorId ) {
        Patient patient = patientRepository.findPatientById(patientId);
        Doctor doctor= doctorRepository.findDoctorById(doctorId);
        if(patient == null || doctor == null) {
            throw new ApiException("Patient or Doctor is not found");
        }
        Booking booking = new Booking(null , LocalDateTime.now(), LocalDate.now(),"","waiting",patient, doctor );

        bookingRepository.save(booking);

    }
    public void removeBooking(Integer userId,Integer bookingId) {
        Patient patient = patientRepository.findPatientById(userId);
        Booking booking=bookingRepository.getBookingById(bookingId);
        if(patient == null || booking == null) {
            throw new ApiException("Patient or Booking is not found");
        }
        bookingRepository.delete(booking);

    }

    public void updateBooking(Integer doctorId,Integer bookingId,String status) {
        Doctor doctor = doctorRepository.findDoctorById(doctorId);
        Booking booking=bookingRepository.getBookingById(bookingId);
        if(doctor == null) {
            throw new ApiException("Doctor is not found");
        }
        if(booking == null) {
            throw new ApiException("Booking is not found");
        }
        if(status.toLowerCase().equalsIgnoreCase("go to hospital")||
        status.toLowerCase().equalsIgnoreCase("wait")  || status.toLowerCase().equalsIgnoreCase("go to plan")) {
            booking.setStatus(status);
            bookingRepository.save(booking);
        }
        throw new ApiException("Booking status is not found");

    }

}

package org.example.capstone3.Controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.Booking;
import org.example.capstone3.Service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//Hussam: general fix
//Mohammed
@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<?> getAllBookings() {
        return ResponseEntity.ok().body(bookingService.getAllBookings());
    }
    //Hussam: fix make it small letters and clear
    @PostMapping("/patient/{patient_id}/doctor/{doctor_id}")
    public ResponseEntity<?> createBooking(@PathVariable Integer patient_id,@PathVariable Integer doctor_id) {
        bookingService.addBooking(patient_id, doctor_id);
        return ResponseEntity.ok().body(new ApiResponse("Booking has been created"));
    }

    //Hussam: fix make it small letters and clear
    @DeleteMapping("/patient/{patient_id}/booking/{booking_id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Integer patient_id,@PathVariable Integer booking_id) {
        bookingService.removeBooking(patient_id, booking_id);
        return ResponseEntity.ok().body(new ApiResponse("Booking has been deleted"));
    }
    //Hussam: fix make it small letters and clear
    @PutMapping("/doctor/{doctor_id}/booking{booking_id}/status/{status}")
    public ResponseEntity<?> updateBooking(@PathVariable Integer doctor_id, @PathVariable Integer booking_id,@PathVariable String status) {
        bookingService.updateBooking(doctor_id, booking_id, status);
        return ResponseEntity.ok().body(new ApiResponse("Booking has been updated"));
    }
}

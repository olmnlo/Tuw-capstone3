package org.example.capstone3.Controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.Booking;
import org.example.capstone3.Service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//Mohammed
@RestController
@RequestMapping("/api/v1/BOOKING")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<?> getAllBookings() {
        return ResponseEntity.ok().body(bookingService.getAllBookings());
    }
    @PostMapping("/patient/{patientId}/doctor/{doctorId}")
    public ResponseEntity<?> createBooking(@PathVariable Integer patientId,@PathVariable Integer doctorId) {
        bookingService.addBooking(patientId, doctorId);
        return ResponseEntity.ok().body(new ApiResponse("Booking has been created"));
    }
    @DeleteMapping("/patient/{patientId}/booking/{bookingId}")
    public ResponseEntity<?> deleteBooking(@PathVariable Integer patientId,@PathVariable Integer bookingId) {
        bookingService.removeBooking(patientId, bookingId);
        return ResponseEntity.ok().body(new ApiResponse("Booking has been deleted"));
    }
    @PutMapping("/doctor/{doctorId}/booking{bookingId}/status/{status}")
    public ResponseEntity<?> updateBooking(@PathVariable Integer doctorId, @PathVariable Integer bookingId,@PathVariable String status) {
        bookingService.updateBooking(doctorId, bookingId, status);
        return ResponseEntity.ok().body(new ApiResponse("Booking has been updated"));
    }
}

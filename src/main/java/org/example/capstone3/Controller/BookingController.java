package org.example.capstone3.Controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.Schedule;
import org.example.capstone3.Service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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
    //Aziz: matching with booking by time slots

    @PostMapping("/patient/{patientId}/doctor/{doctorId}")
    public ResponseEntity<?> createBooking(
            @PathVariable Integer patientId,
            @PathVariable Integer doctorId,
            @RequestBody Map<String, String> requestBody) {

        String dateTimeStr = requestBody.get("appointmentDate");
        LocalDateTime appointmentDate = LocalDateTime.parse(
                dateTimeStr,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );

        bookingService.addBooking(patientId, doctorId, appointmentDate);
        return ResponseEntity.ok(new ApiResponse("Booking has been created"));
    }

    @GetMapping("/doctor/{doctorId}/available-slots")
    public ResponseEntity<?> getAvailableSlots(@PathVariable Integer doctorId) {
        List<Schedule> slots = bookingService.getAvailableSlots(doctorId);
        return ResponseEntity.ok(slots);
    }


//    @PostMapping("/patient/{patient_id}")
//    public ResponseEntity<?> createBooking(@PathVariable Integer patient_id) {
//        bookingService.addBooking(patient_id);
//        return ResponseEntity.ok().body(new ApiResponse("Booking has been created"));
//    }

    //Hussam: fix make it small letters and clear
    //ENDPOINT 4
    @DeleteMapping("/patient/{patient_id}/booking/{booking_id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Integer patient_id, @PathVariable Integer booking_id) {
        bookingService.removeBooking(patient_id, booking_id);
        return ResponseEntity.ok().body(new ApiResponse("Booking has been deleted"));
    }

    //Hussam: fix make it small letters and clear
    //ENDPOINT 5
    @PutMapping("/doctor/{doctor_id}/booking/{booking_id}/status")
    public ResponseEntity<?> updateBooking(@PathVariable Integer doctor_id, @PathVariable Integer booking_id, @RequestParam("status") String status) throws MessagingException {
        bookingService.updateBooking(doctor_id, booking_id, status);
        return ResponseEntity.ok().body(new ApiResponse("Booking has been updated"));
    }

    //Mohammed
    //ENDPOINT 6
    @GetMapping("/patient/{patientId}/status/{status}/on/{on}/from/{from}/to/{to}")
    public ResponseEntity<?> getPatientBookingsSimple(@PathVariable Integer patientId,
                                                      @PathVariable String status,
                                                      @PathVariable String on,   // yyyy-MM-dd (اختياري)
                                                      @PathVariable String from, // yyyy-MM-dd (اختياري)
                                                      @PathVariable String to) {
        return ResponseEntity.ok().body(bookingService.getPatientBookingsSimple(patientId, status, on, from, to));
    }

    //Hussam
    @PostMapping("/nearest/{patient_id}")
    public ResponseEntity<ApiResponse> nearestBooking(@PathVariable Integer patient_id){
        bookingService.takeFastestBooking(patient_id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Booking has been created"));
    }

    @GetMapping("/last-week")
    public ResponseEntity<Map<String, Integer>> getLastWeekAnalysisBooking(){
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.weeklyBookingNumbers());
    }
    @GetMapping("/last-month")
    public ResponseEntity<Map<String, Integer>> getLastMonthAnalysisBooking(){
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.monthlyBookingNumbers());
    }
}

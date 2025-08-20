package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Booking;
import org.example.capstone3.Model.Doctor;
import org.example.capstone3.Model.Patient;
import org.example.capstone3.Model.Schedule;
import org.example.capstone3.Repository.BookingRepository;
import org.example.capstone3.Repository.DoctorRepository;
import org.example.capstone3.Repository.PatientRepository;
import org.example.capstone3.Repository.ScheduleRepository;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

//Mohammed
@Service
@RequiredArgsConstructor
public class BookingService {
    //Hussam fix: final def
    private final BookingRepository bookingRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ScheduleRepository scheduleRepository;

    //Hussam some fix
    public List<Booking> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        if (bookings.isEmpty()) {
            throw new ApiException("booking not found");
        }
        return bookingRepository.findAll();
    }


    //Hussam some fix
    //Aziz - Booking made by time slots
    public void addBooking(Integer patientId, Integer doctorId, LocalDateTime appointmentDate) {

        Patient patient = patientRepository.findPatientById(patientId);
        Doctor doctor = doctorRepository.findDoctorById(doctorId);
        if (patient == null || doctor == null) {
            throw new ApiException("Patient or Doctor not found");
        }

        // 2. check if this slot exists in schedule
        boolean exists = scheduleRepository.existsByDateAndTimeAndDoctor_Id(
                appointmentDate.toLocalDate(),
                appointmentDate.toLocalTime(),
                doctorId
        );
        if (!exists) {
            throw new ApiException("Doctor not available at this time");
        }


        boolean booked = bookingRepository.existsByDoctor_IdAndAppointmentDate(
                doctorId,
                appointmentDate
        );
        if (booked) {
            throw new ApiException("This slot is already booked");
        }

        //  check if patient already has active booking
        Booking lastBooking = bookingRepository
                .findBookingByPatient_IdOrderByAppointmentDateDesc(patientId, Limit.of(1));
        if (lastBooking != null && !lastBooking.getStatus().equalsIgnoreCase("done")) {
            throw new ApiException("You already have another booking");
        }

        //  create booking
        Booking booking = new Booking(
                null,
                appointmentDate,
                "",
                "wait",
                patient,
                doctor
        );

        bookingRepository.save(booking);
    }



    //Hussam some fix
    public void removeBooking(Integer patient_id, Integer booking_id) {
        Patient patient = patientRepository.findPatientById(patient_id);
        Booking booking = bookingRepository.getBookingById(booking_id);
        if (patient == null || booking == null) {
            throw new ApiException("Patient or Booking is not found");
        }
        bookingRepository.delete(booking);

    }
    //Hussam some fix
    public void updateBooking(Integer doctor_id, Integer booking_id, String status) {
        Doctor doctor = doctorRepository.findDoctorById(doctor_id);
        Booking booking = bookingRepository.getBookingById(booking_id);
        if (doctor == null) {
            throw new ApiException("Doctor is not found");
        }
        if (booking == null) {
            throw new ApiException("Booking is not found");
        }
        if (!status.equals("wait") && !status.equals("go-to-hospital") && !status.equals("go-to-plan") && !status.equals("done")) {
            throw new ApiException("Booking status is not found");
        }

        booking.setStatus(status);
        bookingRepository.save(booking);

    }


    //Mohammed Extra end point
    public List<Booking> getPatientBookingsSimple(Integer patientId,
                                                  String status,
                                                  String on,   // yyyy-MM-dd (اختياري)
                                                  String from, // yyyy-MM-dd (اختياري)
                                                  String to) { // yyyy-MM-dd (اختياري)

        // جلب حجوزات المريض فقط
        List<Booking> list = bookingRepository.findByPatient_Id(patientId);
        if (list.isEmpty()) {
            // رجّع قائمة فاضية (أبسط من رمي استثناء)
            return Collections.emptyList();
        }

        LocalDate onDate = parseDate(on);
        LocalDate fromDate = parseDate(from);
        LocalDate toDate = parseDate(to);

        // لو on موجود نتجاهل from/to
        if (onDate != null) {
            fromDate = null;
            toDate = null;
        }

        List<Booking> out = new ArrayList<>();
        for (Booking b : list) {
            // فلتر الحالة (اختياري)
            if (status != null && !status.isBlank()) {
                if (b.getStatus() == null || !b.getStatus().equalsIgnoreCase(status)) continue;
            }

            // فلتر التاريخ
            LocalDate day = (b.getAppointmentDate() != null) ? b.getAppointmentDate().toLocalDate() : null;

            if (onDate != null) {
                if (day == null || !day.equals(onDate)) continue;
            } else {
                if (fromDate != null && (day == null || day.isBefore(fromDate))) continue;
                if (toDate != null && (day == null || day.isAfter(toDate))) continue;
            }

            out.add(b);
        }

        // فرز حسب appointmentDate تصاعدي
        out.sort(Comparator.comparing(Booking::getAppointmentDate,
                Comparator.nullsLast(Comparator.naturalOrder())));

        return out;
    }

    private LocalDate parseDate(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            return LocalDate.parse(s); // يتوقع yyyy-MM-dd
        } catch (DateTimeParseException e) {
            // أبقِها بسيطة: ارجع null بدل رمي استثناء
            return null;
        }

    }

    //Aziz
    public List<Schedule> getAvailableSlots(Integer doctorId) {
        // 1. get all schedule slots for doctor
        List<Schedule> slots = scheduleRepository.findByDoctor_Id(doctorId);

        // 2. get all bookings for doctor
        List<Booking> bookings = bookingRepository.findBookingByDoctor_Id(doctorId);

        // 3. filter out booked ones
        return slots.stream()
                .filter(slot -> bookings.stream()
                        .noneMatch(b ->
                                b.getAppointmentDate().toLocalDate().equals(slot.getDate()) &&
                                        b.getAppointmentDate().toLocalTime().equals(slot.getTime())
                        )
                )
                .toList();
    }


}

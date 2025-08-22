package org.example.capstone3.Service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Booking;
import org.example.capstone3.Model.Doctor;
import org.example.capstone3.Model.Patient;

import org.example.capstone3.Model.Plan;
import org.example.capstone3.Repository.BookingRepository;
import org.example.capstone3.Repository.DoctorRepository;
import org.example.capstone3.Repository.PatientRepository;
import org.example.capstone3.Repository.PlanRepository;
import org.example.capstone3.Model.Schedule;

import org.example.capstone3.Repository.ScheduleRepository;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

//Mohammed
@Service
@RequiredArgsConstructor
public class BookingService {
    //Hussam fix: final def
    private final BookingRepository bookingRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PlanRepository planRepository;

    private final ScheduleRepository scheduleRepository;

    //Mohammed Add
    private final EmailService emailService;


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
        Schedule schedule = scheduleRepository.findByDateAndTimeAndDoctor_Id(appointmentDate.toLocalDate(), appointmentDate.toLocalTime(), doctorId);
        if (schedule == null){
            throw new ApiException("schedule not found");
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



        // 2. Check if schedule is already booked
        boolean booked = bookingRepository.existsBySchedule(schedule);
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
                doctor,
                schedule
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
    public void updateBooking(Integer doctor_id, Integer booking_id, String status) throws MessagingException {
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

        //Mohammed Add

        if(status.equalsIgnoreCase("go-to-hospital")) {
            emailService.sendHospitalInstructionEmail(booking.getPatient().getEmail());
        }

        //******************************************************************************************

        //Mohammed Add plan automatically to patient
        //Hussam fix add new Array list
        Plan  plan=new Plan(null,"","",booking.getPatient(),doctor,new ArrayList<>());
        if(status.equalsIgnoreCase("go-to-plan")){

            planRepository.save(plan);
            System.out.println();
        }

        //******************************************************************************************

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


//    public void takeFastestBooking(Integer patient_id){
//        Patient patient = patientRepository.findPatientById(patient_id);
//        if (patient == null){
//            throw new ApiException("patient not found");
//        }
//        List<Schedule> schedules = scheduleRepository.findUpcomingSchedules(LocalDate.now(), LocalTime.now());
//        for (Schedule schedule : schedules) {
//            LocalDateTime scheduleDateTime = LocalDateTime.of(schedule.getDate(), schedule.getTime());
//            boolean doctorBusy = bookingRepository.existsByDoctor_IdAndAppointmentDateAfter(
//                    schedule.getDoctor().getId(),
//                    LocalDateTime.now()
//            );
//            if (!doctorBusy) {
//                Booking booking = new Booking(null, scheduleDateTime, "", "wait", patient, schedule.getDoctor());
//                bookingRepository.save(booking);
//                return;
//            }
//        }
//
//    }
    public void takeFastestBooking(Integer patientId) {
        Patient patient = patientRepository.findPatientById(patientId);
        if (patient == null) {
            throw new ApiException("Patient not found");
        }

        List<Schedule> schedules = scheduleRepository.findUpcomingSchedules(LocalDate.now(), LocalTime.now());

        for (Schedule schedule : schedules) {
            LocalDateTime scheduleDateTime = LocalDateTime.of(schedule.getDate(), schedule.getTime());

            // Check if doctor already has a booking exactly at this schedule
            boolean doctorBusy = bookingRepository.existsByDoctor_IdAndAppointmentDate(
                    schedule.getDoctor().getId(),
                    scheduleDateTime
            );

            if (!doctorBusy) {
                Booking booking = new Booking(null, scheduleDateTime, "", "wait", patient, schedule.getDoctor(), schedule);
                bookingRepository.save(booking);
                return;
            }
        }

        throw new ApiException("No available schedule found for booking");
    }

    public Map<String , Integer> weeklyBookingNumbers(){
        List<Booking> bookings = bookingRepository.findBookingByAppointmentDateBetween(LocalDate.now().minusDays(7).atStartOfDay(), LocalDate.now().plusDays(1).atStartOfDay());

        return bookings.stream()
                .collect(Collectors.toMap(
                        b -> b.getDoctor().getId() + " - " + b.getDoctor().getName(), // unique key
                        b -> 1,
                        Integer::sum
                ));
    }

    public Map<String, Integer> monthlyBookingNumbers() {
        List<Booking> bookings = bookingRepository.findBookingByAppointmentDateBetween(
                LocalDate.now().withDayOfMonth(1).atStartOfDay(),              // first day of this month
                LocalDate.now().plusMonths(1).withDayOfMonth(1).atStartOfDay() // first day of next month
        );
        return bookings.stream()
                .collect(Collectors.toMap(
                        b -> b.getDoctor().getId() + " - " + b.getDoctor().getName(), // unique key
                        b -> 1,
                        Integer::sum
                ));
    }


}

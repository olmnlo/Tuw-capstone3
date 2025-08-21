package org.example.capstone3.Service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.DTOin.PatientDTO;
import org.example.capstone3.Model.Booking;
import org.example.capstone3.Model.Patient;
import org.example.capstone3.Model.Plan;
import org.example.capstone3.Model.Report;
import org.example.capstone3.Repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
//Hussam
public class PatientService {
    private final PatientRepository patientRepository;
    private final EmailService emailService;
    //Hussam
    public List<Patient> findAllPatient(){
        List<Patient> patients = patientRepository.findAll();
        if (patients.isEmpty()){
            throw new ApiException("no patients");
        }
        return patients;
    }

    //Hussam
    public void addPatient(PatientDTO patientDTO){
        //Mohammed add Email
        Patient patient = new Patient(null, patientDTO.getName(),patientDTO.getUsername(),patientDTO.getPassword(), patientDTO.getAge(), patientDTO.getSex(), patientDTO.getEmail(), new ArrayList<>(),null ,new ArrayList<>(), new ArrayList<>());
        patientRepository.save(patient);

        //Mohammed add

        try {
            emailService.sendWelcomePatientEmail(patient.getEmail(), patient.getName());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }

    //Hussam
    public void updatePatient(Integer patient_id, PatientDTO patient){
        Patient oldPatient = patientRepository.findPatientById(patient_id);
        if (oldPatient == null){
            throw new ApiException("patient not found");
        }
        oldPatient.setAge(patient.getAge());
        oldPatient.setName(patient.getName());
        oldPatient.setPassword(patient.getPassword());
        oldPatient.setUsername(patient.getUsername());
        oldPatient.setReport(new ArrayList<>());
        oldPatient.setBooking(new ArrayList<>());




        //Mohammed add
        oldPatient.setEmail(patient.getEmail());
        oldPatient.setAnswers(new ArrayList<>());

        patientRepository.save(oldPatient);



        //Mohammed Add

        try {
            emailService.sendPatientProfileUpdatedEmail(oldPatient.getEmail(), oldPatient.getName());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }
    //Hussam
    public void deletePatient(Integer patient_id) {
        Patient oldPatient = patientRepository.findPatientById(patient_id);
        if (oldPatient == null) {
            throw new ApiException("patient not found");
        }
        patientRepository.delete(oldPatient);
    }

    //Mohammed
    public ApiResponse sendPdfReportToPatient(Integer patient_id, Integer doctor_id){
        Patient patient = patientRepository.findPatientById(patient_id);
        if (patient == null) {
            throw new ApiException("Patient not found");
        }

        try {
            emailService.sendPatientReportEmail(patient.getEmail(), patient.getName(), patient_id,doctor_id);
            return new ApiResponse("Report sent successfully to " + patient.getEmail());
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send report email", e);
        }
    }

}
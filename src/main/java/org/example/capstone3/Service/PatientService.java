package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
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
        Patient patient = new Patient(null, patientDTO.getName(),patientDTO.getUsername(),patientDTO.getPassword(), patientDTO.getAge(), patientDTO.getSex(), new ArrayList<>(), new Plan(), new ArrayList<>(), new ArrayList<>());
        patientRepository.save(patient);
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
        oldPatient.setQuestion(new ArrayList<>());

        patientRepository.save(oldPatient);
    }

    public void deletePatient(Integer patient_id) {
        Patient oldPatient = patientRepository.findPatientById(patient_id);
        if (oldPatient == null) {
            throw new ApiException("patient not found");
        }
        patientRepository.delete(oldPatient);
    }
}
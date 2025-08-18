package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.DTO.PatientDTO;
import org.example.capstone3.Model.Patient;
import org.example.capstone3.Repository.PatientRepository;
import org.springframework.stereotype.Service;

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
        Patient patient = new Patient(null, patientDTO.getName(),patientDTO.getUsername(),patientDTO.getPassword(), patientDTO.getAge(), patientDTO.getSex(), null, null, null, null);
        patientRepository.save(patient);
    }

    //Hussam
    public void updatePatient(Integer patient_id, Patient patient){

    }
}
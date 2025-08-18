package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
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
}
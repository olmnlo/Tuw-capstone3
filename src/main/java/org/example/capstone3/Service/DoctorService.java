package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Doctor;
import org.example.capstone3.Repository.DoctorRepository;
import org.example.capstone3.Repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
//Aziz
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public List<Doctor> findAllDoctor(){
        List<Doctor> doctors = doctorRepository.findAll();
        if (doctors.isEmpty()){
            throw new ApiException("no doctor found !");
        }
        return doctors;
    }


    public void addDoctor(Doctor doctor){
        doctorRepository.save(doctor);
    }

    public void updateDoctor(Integer id, Doctor doctor){
        Doctor oldDoctor = doctorRepository.findDoctorById(id);

        if (oldDoctor == null){
            throw new ApiException("no doctor found !");
        }

        oldDoctor.setAge(doctor.getAge());
        oldDoctor.setName(doctor.getName());
        oldDoctor.setPassword(doctor.getPassword());
        oldDoctor.setUsername(doctor.getUsername());

        doctorRepository.save(oldDoctor);
    }

    public void deleteDoctor(Integer id){
        Doctor doctor = doctorRepository.findDoctorById(id);

        if (doctor == null){
            throw new ApiException("no doctor found !");
        }

        doctorRepository.delete(doctor);
    }




}
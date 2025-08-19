package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.DTOin.DoctorDTO;
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

    public List<Doctor> findAllDoctors(){
        List<Doctor> doctors = doctorRepository.findAll();
        if (doctors.isEmpty()){
            throw new ApiException("no doctor found !");
        }
        return doctors;
    }

    //Hussam some fix
    public void addDoctor(DoctorDTO doctorDTO){
        Doctor doctor = new Doctor(null, doctorDTO.getUsername(),doctorDTO.getPassword(),doctorDTO.getName(),doctorDTO.getAge(),doctorDTO.getSex(),null,null,null);
        doctorRepository.save(doctor);
    }

    //Hussam some fix
    public void updateDoctor(Integer id, DoctorDTO doctorDTO){
        Doctor oldDoctor = doctorRepository.findDoctorById(id);

        if (oldDoctor == null){
            throw new ApiException("no doctor found !");
        }
        //Hussam fix
        oldDoctor.setAge(doctorDTO.getAge());
        oldDoctor.setName(doctorDTO.getName());
        oldDoctor.setPassword(doctorDTO.getPassword());
        oldDoctor.setUsername(doctorDTO.getUsername());
        oldDoctor.setSex(doctorDTO.getSex());

        doctorRepository.save(oldDoctor);
    }

    public void deleteDoctor(Integer id){
        Doctor doctor = doctorRepository.findDoctorById(id);

        if (doctor == null){
            throw new ApiException("no doctor found !");
        }

        doctorRepository.delete(doctor);
    }

    // assign method for schedule (object inputs or variables) look at the model to know the parameters

    // assign method for booking (object inputs or variables) look at the model to know the parameters

    // assign method for report (object inputs or variables) look at the model to know the parameters





}
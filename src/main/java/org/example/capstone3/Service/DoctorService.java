package org.example.capstone3.Service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.DTOin.DoctorDTO;
import org.example.capstone3.DTOin.ScheduleDTO;
import org.example.capstone3.DTOout.DoctorDTOout;
import org.example.capstone3.Model.Doctor;
import org.example.capstone3.Repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
//Aziz
public class DoctorService {
    private final DoctorRepository doctorRepository;

    //Mohammed
    private final EmailService emailService;

    public List<Doctor> findAllDoctors(){
        List<Doctor> doctors = doctorRepository.findAll();
        if (doctors.isEmpty()){
            throw new ApiException("no doctor found !");
        }
        return doctors;
    }

    //Hussam some fix
    public void addDoctor(DoctorDTO doctorDTO){
        Doctor doctor = new Doctor(null, doctorDTO.getUsername(),doctorDTO.getPassword(),doctorDTO.getName(),doctorDTO.getAge(),doctorDTO.getSex(), doctorDTO.getEmail(), null,null,null);
        doctorRepository.save(doctor);

        //Mohammed

        try {
            emailService.sendWelcomeDoctorEmail(doctor.getEmail(), doctor.getName());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
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

        //Mohammed
        try {
            emailService.sendDoctorProfileUpdatedEmail(oldDoctor.getEmail(), oldDoctor.getName());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDoctor(Integer id){
        Doctor doctor = doctorRepository.findDoctorById(id);

        if (doctor == null){
            throw new ApiException("no doctor found !");
        }

        doctorRepository.delete(doctor);
    }

    public List<DoctorDTOout> searchDoctorByNameContains(String key){
        List<DoctorDTOout> doctorDTOouts = new ArrayList<>();
        List<Doctor> doctors = doctorRepository.findDoctorByNameContains(key);
        for (Doctor doctor : doctors){
            doctorDTOouts.add(new DoctorDTOout(doctor.getName(), doctor.getSex(),doctor.getSchedule()));
        }
        return doctorDTOouts;
    }

    // get all doctors based on slots
    public List<DoctorDTOout> getAvailableDoctors(ScheduleDTO scheduleDTO) {
        return doctorRepository.findBySchedule_DateAndSchedule_Time(
                scheduleDTO.getDate(),
                scheduleDTO.getTime()
        );
    }






}
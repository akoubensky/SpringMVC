package com.example.services;

import com.example.model.Doctor;
import com.example.model.Patient;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Component
@Service
@Getter
public class HospitalService {
    private final List<Doctor> doctors = new ArrayList<>();
    private final List<Patient> patients = new ArrayList<>();

    public void addDoctor(Doctor d) {
        doctors.add(d);
    }
    public void addPatient(Patient p) {
        patients.add(p);
    }
}

package com.example.controllers;

import com.example.model.Doctor;
import com.example.model.Patient;
import com.example.services.HospitalService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HospitalController {

  private final HospitalService hospitalService;

  public HospitalController(HospitalService hospitalService) {
    this.hospitalService = hospitalService;
  }

  @GetMapping("/doctors")
  public String viewDoctors(Model model) {
    List<Doctor> doctors = hospitalService.getDoctors();
    model.addAttribute("doctors", doctors);

    return "doctors.html";
  }

  @PostMapping("/doctors")
  public String addDoctor(
      @RequestParam("name") String name,
      String position,
      Model model
  ) {
    Doctor d = new Doctor(name, position);
    hospitalService.addDoctor(d);

    List<Doctor> doctors = hospitalService.getDoctors();
    model.addAttribute("doctors", doctors);

    return "doctors.html";
  }

  @GetMapping("/patients")
  public String viewPatients(Model model) {
    List<PatientView> patients = patientsToView(hospitalService.getPatients());
    model.addAttribute("patients", patients);

    return "patients.html";
  }

  @PostMapping("/patients")
  public String addPatient(String name, String illness, String doctor,
                           Model model) {
    List<Doctor> doctors = hospitalService.getDoctors().stream()
            .filter(d -> doctor.equalsIgnoreCase(d.getName()))
            .collect(Collectors.toList());
    if (!doctors.isEmpty()) {
      Patient patient = new Patient(name, illness, doctors.get(0));
      hospitalService.addPatient(patient);
    }
    List<PatientView> patientView = patientsToView(hospitalService.getPatients());
    model.addAttribute("patients", patientView);

    return "patients.html";
  }

  private static List<PatientView> patientsToView(List<Patient> patients) {
    return patients.stream()
            .map(p -> new PatientView(p.getName(), p.getIllness(), p.getDoctor().getName()))
            .collect(Collectors.toList());
  }

  @AllArgsConstructor
  @Getter
  private static class PatientView {
    String name;
    String illness;
    String doctor;
  }
}

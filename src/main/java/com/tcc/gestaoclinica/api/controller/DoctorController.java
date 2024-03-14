package com.tcc.gestaoclinica.api.controller;

import com.tcc.gestaoclinica.api.dto.request.DoctorRequest;
import com.tcc.gestaoclinica.api.dto.response.DoctorResponse;
import com.tcc.gestaoclinica.domain.models.Doctor;
import com.tcc.gestaoclinica.domain.repositories.DoctorRepository;
import com.tcc.gestaoclinica.domain.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

        @Autowired
        private DoctorService doctorService;

        @Autowired
        private DoctorRepository doctorRepository;

        @GetMapping
        public ResponseEntity<Page<DoctorResponse>> getDoctors(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size,
                                                               @RequestParam(required = false) String name) {
            Pageable pageable = PageRequest.of(page, size);
            Page<Doctor> doctors;
            if (name != null && !name.isEmpty()) {
                doctors = doctorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name, pageable);
            } else {
                doctors = doctorRepository.findAll(pageable);
            }
            Page<DoctorResponse> responses = doctors.map(this::doctorToDoctorResponse);

            return ResponseEntity.ok(responses);
        }

        @GetMapping("/{doctorId}")
        public ResponseEntity<DoctorResponse> getDoctor(@PathVariable Long doctorId) {
            Doctor doctor = doctorService.searchOrFail(doctorId);
            DoctorResponse doctorDto = doctorToDoctorResponse(doctor);
            return ResponseEntity.ok(doctorDto);
        }

        @PostMapping
        public ResponseEntity<DoctorResponse> createDoctor(@RequestBody DoctorRequest createDoctorDto) {
            Doctor doctor = doctorService.createDoctor(doctorRequestToDoctor(createDoctorDto));
            DoctorResponse doctorResponse = doctorToDoctorResponse(doctor);
            return new ResponseEntity<>(doctorResponse, HttpStatus.CREATED);
        }

        @PutMapping("/{doctorId}")
        public ResponseEntity<Void> updateDoctor(@PathVariable Long doctorId, @RequestBody DoctorRequest createDoctorDto) {
            doctorService.updateDoctor(doctorId, doctorRequestToDoctor(createDoctorDto));
            return new ResponseEntity<>(HttpStatus.OK);
        }

        public DoctorResponse doctorToDoctorResponse(Doctor doctor) {
            DoctorResponse doctorResponse = new DoctorResponse();
            doctorResponse.setId(doctor.getId());
            doctorResponse.setFirstName(doctor.getFirstName());
            doctorResponse.setLastName(doctor.getLastName());
            doctorResponse.setPhone(doctor.getPhone());
            doctorResponse.setEmail(doctor.getEmail());
            doctorResponse.setCrm(doctor.getCrm());
            doctorResponse.setSpecialty(doctor.getSpecialty());

            return doctorResponse;
        }

        public Doctor doctorRequestToDoctor(DoctorRequest doctorRequest) {
            return Doctor.builder()
                    .firstName(doctorRequest.getFirstName())
                    .lastName(doctorRequest.getLastName())
                    .phone(doctorRequest.getPhone())
                    .email(doctorRequest.getEmail())
                    .crm(doctorRequest.getCrm())
                    .specialty(doctorRequest.getSpecialty())
                    .build();
        }
}

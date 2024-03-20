package com.tcc.gestaoclinica.api.controller;

import com.tcc.gestaoclinica.api.dto.request.GroupMedicalCareRequest;
import com.tcc.gestaoclinica.api.dto.response.*;
import com.tcc.gestaoclinica.domain.models.Doctor;
import com.tcc.gestaoclinica.domain.models.GroupMedicalCare;
import com.tcc.gestaoclinica.domain.models.Patient;
import com.tcc.gestaoclinica.domain.models.User;
import com.tcc.gestaoclinica.domain.repositories.GroupMedialCareRepository;
import com.tcc.gestaoclinica.domain.services.DoctorService;
import com.tcc.gestaoclinica.domain.services.GroupMedicalCareService;
import com.tcc.gestaoclinica.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/group-medical-care")
public class GroupMedicalCareController {

    @Autowired
    private GroupMedicalCareService groupMedicalCareService;

    @Autowired
    private GroupMedialCareRepository groupMedialCareRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserService userService;

    @GetMapping("/{groupMedicalCareId}")
    public GroupMedicalCareResponse getALl(@PathVariable Long groupMedicalCareId) {
        GroupMedicalCare groupMedicalCare = groupMedicalCareService.searchOrFail(groupMedicalCareId);

        return toResponse(groupMedicalCare);
    }

    @GetMapping
    public ResponseEntity<Page<GroupMedicalCareResponse>> getALl(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(required = false) String name) {

        Pageable pageable = PageRequest.of(page, size);
        Page<GroupMedicalCare> gmc;
        if (name != null && !name.isEmpty()) {
            gmc = groupMedialCareRepository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            gmc = groupMedialCareRepository.findAll(pageable);
        }
        Page<GroupMedicalCareResponse> responses = gmc.map(this::toResponse);

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{groupMedicalCareId}/add-patient/{patientId}")
    public void addPatientToGroupMedicalCare(@PathVariable Long groupMedicalCareId, @PathVariable Long patientId) {
        groupMedicalCareService.addPatientToGroupMedicalCare(groupMedicalCareId, patientId);
    }

    @DeleteMapping("/{groupMedicalCareId}/remove-patient/{patientId}")
    public void removePatientFromGroupMedicalCare(@PathVariable Long groupMedicalCareId, @PathVariable Long patientId) {
        groupMedicalCareService.removePatientFromGroupMedicalCare(groupMedicalCareId, patientId);
    }

    @PostMapping
    public void createGroupMedicalCare(@RequestBody GroupMedicalCareRequest groupMedicalCareRequest) {
        GroupMedicalCare groupMedicalCare = new GroupMedicalCare();

        groupMedicalCare.setName(groupMedicalCareRequest.getName());
        groupMedicalCare.setDate(groupMedicalCareRequest.getDate());

        Doctor doctor = doctorService.searchOrFail(groupMedicalCareRequest.getDoctorId());
        groupMedicalCare.getDoctors().add(doctor);

        User user = userService.buscarOuFalhar(groupMedicalCareRequest.getUserId());
        groupMedicalCare.setUser(user);

        groupMedicalCare.setDate(groupMedicalCareRequest.getDate());

        groupMedicalCareService.save(groupMedicalCare);
    }

    public GroupMedicalCareResponse toResponse(GroupMedicalCare groupMedicalCare) {
        GroupMedicalCareResponse groupMedicalCareResponse = new GroupMedicalCareResponse();
        groupMedicalCareResponse.setId(groupMedicalCare.getId());
        var nameGroup = groupMedicalCare.getDate().toString();
        groupMedicalCareResponse.setName("Turma " + converterFormatoData(nameGroup));
        groupMedicalCareResponse.setDate(groupMedicalCare.getDate().toString());

        UserGroupResponse userGroupResponse = new UserGroupResponse();
        userGroupResponse.setId(groupMedicalCare.getUser().getId());
        String nameUser = groupMedicalCare.getUser().getFirstName() + " " + groupMedicalCare.getUser().getLastName();
        userGroupResponse.setName(nameUser);

        groupMedicalCareResponse.setUser(userGroupResponse);


        var patients = groupMedicalCare.getPatients().stream().map(patient -> {
            PatientGroupResponse patientResponse = new PatientGroupResponse();
            patientResponse.setId(patient.getId());
            patientResponse.setName(patient.getFirstName() + " " + patient.getLastName());
            patientResponse.setEmail(patient.getEmail());
            patientResponse.setPhone(patient.getPhone());

            return patientResponse;
        });

        groupMedicalCareResponse.setPatients(patients.toList());

        List<Doctor> doctors = groupMedicalCare.getDoctors();

        var doctorsResponse = doctors.stream().map(doctor -> {
            DoctorGroupResponse doctorGroupResponse = new DoctorGroupResponse();
            doctorGroupResponse.setId(doctor.getId());
            doctorGroupResponse.setName(doctor.getFirstName() + " " + doctor.getLastName());
            return doctorGroupResponse;
        });

        groupMedicalCareResponse.setDoctors(doctorsResponse.toList());

        return groupMedicalCareResponse;
    }

    public static String converterFormatoData(String dataHoraString) {
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraString);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        return dataHora.format(formatter);
    }


}


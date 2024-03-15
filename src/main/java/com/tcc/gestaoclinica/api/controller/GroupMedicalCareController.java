package com.tcc.gestaoclinica.api.controller;

import com.tcc.gestaoclinica.api.dto.request.GroupMedicalCareRequest;
import com.tcc.gestaoclinica.api.dto.response.DoctorGroupResponse;
import com.tcc.gestaoclinica.api.dto.response.GroupMedicalCareResponse;
import com.tcc.gestaoclinica.api.dto.response.UserGroupResponse;
import com.tcc.gestaoclinica.domain.models.Doctor;
import com.tcc.gestaoclinica.domain.models.GroupMedicalCare;
import com.tcc.gestaoclinica.domain.models.User;
import com.tcc.gestaoclinica.domain.repositories.GroupMedialCareRepository;
import com.tcc.gestaoclinica.domain.services.DoctorService;
import com.tcc.gestaoclinica.domain.services.GroupMedicalCareService;
import com.tcc.gestaoclinica.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        groupMedicalCareResponse.setName(groupMedicalCare.getName());
        groupMedicalCareResponse.setDate(groupMedicalCare.getDate().toString());

        UserGroupResponse userGroupResponse = new UserGroupResponse();
        userGroupResponse.setId(groupMedicalCare.getUser().getId());
        String name = groupMedicalCare.getUser().getFirstName() + " " + groupMedicalCare.getUser().getLastName();
        userGroupResponse.setName(name);

        groupMedicalCareResponse.setUser(userGroupResponse);
        groupMedicalCareResponse.setPatients(null);

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


}


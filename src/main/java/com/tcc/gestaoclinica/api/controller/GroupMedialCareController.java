package com.tcc.gestaoclinica.api.controller;


import com.tcc.gestaoclinica.domain.repositories.GroupMedicalCareRepository;
import com.tcc.gestaoclinica.domain.services.GroupMedicalCareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group-medical-care")
public class GroupMedialCareController {

    @Autowired
    private GroupMedicalCareService groupMedicalCareService;

    @Autowired
    private GroupMedicalCareRepository groupMedicalCareRepository;



}

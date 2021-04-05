package com.SafetyNet.Alerts.controller;


import com.SafetyNet.Alerts.model.MedicalRecord;
import com.SafetyNet.Alerts.service.FireStationService;
import com.SafetyNet.Alerts.service.MedicalRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicalRecordsController {


    private final MedicalRecordService medicalRecordService;

    MedicalRecordsController(FireStationService fireStationService, MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;

    }


    // add a medicalRecord
    @PostMapping(value = "medicalRecord")
    public void addAMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.addMedicalRecord(medicalRecord);
    }

    // update a medicalRecord
    @PutMapping(value = "medicalRecord")
    public void updateAMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.updateMedicalRecord(medicalRecord);
    }

    // delete a medicalRecord
    @DeleteMapping(value = "medicalRecord")
    public void deleteAMedicalRecord(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }

    // gel all medical records
    @RequestMapping(value = "medicalRecord", method = RequestMethod.GET)
    public List<MedicalRecord> allMedicalRecords() {

        return this.medicalRecordService.allMedicalRecords();
    }


}

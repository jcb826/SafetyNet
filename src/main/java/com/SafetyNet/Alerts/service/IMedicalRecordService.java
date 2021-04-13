package com.SafetyNet.Alerts.service;

import com.SafetyNet.Alerts.model.MedicalRecord;

import java.util.List;


public interface IMedicalRecordService {

    void addMedicalRecord(MedicalRecord medicalRecord);

    void updateMedicalRecord(MedicalRecord medicalRecord);

    void deleteMedicalRecord(String firstName, String lastName);

    List<MedicalRecord> allMedicalRecords();

}



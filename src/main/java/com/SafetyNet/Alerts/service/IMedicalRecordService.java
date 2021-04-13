package com.SafetyNet.Alerts.service;

import com.SafetyNet.Alerts.model.MedicalRecord;
import com.SafetyNet.Alerts.repository.MedicalRecordsRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IMedicalRecordService {




     void addMedicalRecord (MedicalRecord medicalRecord);



     void updateMedicalRecord(MedicalRecord medicalRecord);

     void deleteMedicalRecord(String firstName,String lastName);


   List<MedicalRecord> allMedicalRecords();



}



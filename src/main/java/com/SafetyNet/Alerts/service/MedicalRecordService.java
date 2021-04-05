package com.SafetyNet.Alerts.service;

import com.SafetyNet.Alerts.model.FireStation;
import com.SafetyNet.Alerts.model.MedicalRecord;
import com.SafetyNet.Alerts.repository.MedicalRecordsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordsRepository medicalRecordsRepository;

    public MedicalRecordService(MedicalRecordsRepository medicalRecordsRepository) {
        this.medicalRecordsRepository = medicalRecordsRepository;
    }


    public void addMedicalRecord (MedicalRecord medicalRecord){

        medicalRecordsRepository.saveMedicalRecord(medicalRecord);
    }

    public void updateMedicalRecord(MedicalRecord medicalRecord) {

        medicalRecordsRepository.updateMedicalRecord(medicalRecord);
    }

    public void deleteMedicalRecord(String firstName,String lastName) {

        medicalRecordsRepository.deleteMedicalRecord(firstName,lastName);
    }
    public List<MedicalRecord> allMedicalRecords()
    {
        return medicalRecordsRepository.findAllMedicalRecords();
    }


}



package com.SafetyNet.Alerts;

import com.SafetyNet.Alerts.controller.MedicalRecordsController;
import com.SafetyNet.Alerts.model.MedicalRecord;
import com.SafetyNet.Alerts.repository.MedicalRecordsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MedicalRecordsControllerTest {

    @Autowired
    MedicalRecordsRepository medicalRecordsRepository;

    @Autowired
    MedicalRecordsController medicalRecordsController;


    private MedicalRecord getMedicalrecord() {
        MedicalRecord medicalRecord = new MedicalRecord();
        String[] medications = {"medication test", "medication test2"};
        medicalRecord.setMedications(medications);
        medicalRecord.setLastName("lastNameTest");
        medicalRecord.setFirstName("firstNameTest");
        medicalRecord.setBirthdate("01/08/1986");
        String[] allergies = {"allergie test", "allergies test2"};
        medicalRecord.setAllergies(allergies);
        return medicalRecord;
    }


    @Test
    void addMedicalRecordTest() {
        MedicalRecord medicalRecord = getMedicalrecord();
        medicalRecordsController.addAMedicalRecord(medicalRecord);
        MedicalRecord result = medicalRecordsRepository.findMedicalWithFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());
        assert (result.getFirstName().equals("firstNameTest") && (result.getLastName().equals("lastNameTest")));
    }

    @Test
    void updateAMedicalRecordTest() {
        MedicalRecord medicalRecord = getMedicalrecord();
        medicalRecordsController.addAMedicalRecord(medicalRecord);
        medicalRecord.setFirstName("firstNameUpdated");
        medicalRecordsController.updateAMedicalRecord(medicalRecord);
        MedicalRecord result = medicalRecordsRepository.findMedicalWithFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());
        assert (result.getFirstName().equals("firstNameUpdated"));
    }

    @Test
    void deleteMedicalRecordTest() {
        MedicalRecord medicalRecord = getMedicalrecord();
        medicalRecord.setFirstName("firstNameTest3");
        medicalRecord.setLastName("lastNameTest3");
        medicalRecordsController.addAMedicalRecord(medicalRecord);
        medicalRecordsController.deleteAMedicalRecord("firstNameTest3", "lastNameTest3");
        MedicalRecord result = medicalRecordsRepository.findMedicalWithFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());
        assertThat(result.getFirstName()).isNull();
    }


    @Test
    void allMedicalRecordsTest() {

        List<MedicalRecord> medicalRecords = medicalRecordsController.allMedicalRecords();

        Assert.notNull(medicalRecords, "medicalRecords is not null ");

    }


}

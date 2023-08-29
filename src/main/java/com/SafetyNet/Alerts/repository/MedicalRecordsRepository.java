package com.SafetyNet.Alerts.repository;

import com.SafetyNet.Alerts.model.FireStation;
import com.SafetyNet.Alerts.model.MedicalRecord;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicalRecordsRepository {
    private final DataHandler dataHandler;

    public MedicalRecordsRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public List<MedicalRecord> findAllMedicalRecords() {
        return dataHandler.getData().getMedicalrecords();
    }

    private boolean isUnder18(String birthdate) {

        Date date = null;
        try {
            date = new SimpleDateFormat("DD/MM/YYYY").parse(birthdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 18);
        // a tester / 365.25 System.out.println(LocalDate.parse(birthdate).datesUntil(LocalDate.now()).count());
        return !calendar.getTime().after(date);

    }


    public List<MedicalRecord> findAllMedicalRecordsUnder18() {

        return dataHandler.getData().getMedicalrecords().stream().filter(m -> isUnder18(m.getBirthdate())).collect(Collectors.toList());

    }

    public MedicalRecord findMedicalWithFirstNameAndLastName(String firstName, String lastName) {
        return dataHandler.getData().getMedicalrecords().stream()
                .filter(p -> p.getFirstName().equals(firstName))
                .filter(p -> p.getLastName().equals(lastName))
                .findFirst()
                .orElseGet(() -> new MedicalRecord());

    }

    public List<MedicalRecord> findAllMedicalRecords(String address) {

        return dataHandler.getData().getMedicalrecords();

    }


    public void saveMedicalRecord(MedicalRecord medicalRecord) {
        dataHandler.getData().getMedicalrecords().add(medicalRecord);
        dataHandler.save();
    }

    public void updateMedicalRecord (MedicalRecord medicalRecord){

        List<MedicalRecord> medicalRecords = dataHandler.getData().getMedicalrecords();
        for (MedicalRecord medicalRecord2 : medicalRecords) {

            if (medicalRecord2.getLastName().equals((medicalRecord.getLastName()))&&medicalRecord2.getFirstName().equals(medicalRecord.getFirstName())) {

                medicalRecord2.setMedications(medicalRecord.getMedications());
                medicalRecord2.setAllergies(medicalRecord.getAllergies());
                medicalRecord2.setBirthdate(medicalRecord.getBirthdate());
            }
        }


        // look for the person to update

        // set the new properties of this person
        dataHandler.getData().setMedicalrecords(medicalRecords);
        dataHandler.save();
    }

    public void deleteMedicalRecord(String firstName, String lastName) {

        List<MedicalRecord> medicalRecords = dataHandler.getData().getMedicalrecords();
        Integer count = null;
        for (int i = 0; i < medicalRecords.size(); i++) {

            if (medicalRecords.get(i).getFirstName().equals(firstName) && medicalRecords.get(i).getLastName().equals(lastName)) {
                count = i;

            }


        }
        if (count != null ){
                medicalRecords.remove(count.intValue());
            dataHandler.getData().setMedicalrecords(medicalRecords);
            dataHandler.save();
        }
    }




    }




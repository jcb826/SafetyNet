package com.SafetyNet.Alerts.service;

import com.SafetyNet.Alerts.model.Person;
import com.SafetyNet.Alerts.service.dto.ChildAlertDto;
import com.SafetyNet.Alerts.service.dto.FireDto;
import com.SafetyNet.Alerts.service.dto.PersonInfoDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public interface IPersonService {


    List<String> findAllEmailsByCity(String city);

    List<ChildAlertDto> findAllchildsUnder18ByAddress(String address);


    List<PersonInfoDto> findAllpersonsWithMedicalRecords(String firstName, String lastName);


    private int computeAge(String birthdateOfPerson) {
        Date date = null;
        Calendar now = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(birthdateOfPerson);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        birthDate.setTime(date);
        if (birthDate.after(now)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }
        int year1 = now.get(Calendar.YEAR);
        int year2 = birthDate.get(Calendar.YEAR);
        int age = year1 - year2;
        int month1 = now.get(Calendar.MONTH);
        int month2 = birthDate.get(Calendar.MONTH);
        if (month2 > month1) {
            age--;
        } else if (month1 == month2) {
            int day1 = now.get(Calendar.DAY_OF_MONTH);
            int day2 = birthDate.get(Calendar.DAY_OF_MONTH);
            if (day2 > day1) {
                age--;
            }
        }
        return age;
    }


    List<FireDto> findAllpersonsWithMedicalRecords(String address);

    void addPerson(Person person);

    void updateAPerson(Person person);

    void deletePerson(String firstName, String lastName);

    List<Person> people();

}




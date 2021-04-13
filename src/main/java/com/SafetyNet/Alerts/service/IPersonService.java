package com.SafetyNet.Alerts.service;

import com.SafetyNet.Alerts.model.Person;
import com.SafetyNet.Alerts.service.dto.ChildAlertDto;
import com.SafetyNet.Alerts.service.dto.FireDto;
import com.SafetyNet.Alerts.service.dto.PersonInfoDto;

import java.util.List;


public interface IPersonService {

    List<String> findAllEmailsByCity(String city);

    List<ChildAlertDto> findAllchildsUnder18ByAddress(String address);

    List<PersonInfoDto> findAllpersonsWithMedicalRecords(String firstName, String lastName);

    List<FireDto> findAllpersonsWithMedicalRecords(String address);

    void addPerson(Person person);

    void updateAPerson(Person person);

    void deletePerson(String firstName, String lastName);

    List<Person> people();

}




package com.SafetyNet.Alerts.service;

import com.SafetyNet.Alerts.model.FireStation;
import com.SafetyNet.Alerts.model.MedicalRecord;
import com.SafetyNet.Alerts.model.Person;
import com.SafetyNet.Alerts.repository.FireStationRepository;
import com.SafetyNet.Alerts.repository.MedicalRecordsRepository;
import com.SafetyNet.Alerts.repository.PersonRepository;
import com.SafetyNet.Alerts.service.dto.ChildAlertDto;
import com.SafetyNet.Alerts.service.dto.FireDto;
import com.SafetyNet.Alerts.service.dto.PersonInfoDto;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService implements IPersonService {

    private final PersonRepository personRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;
    private final FireStationRepository fireStationRepository;

    public PersonService(PersonRepository personRepository, MedicalRecordsRepository medicalRecordsRepository, FireStationRepository fireStationRepository) {
        this.personRepository = personRepository;
        this.medicalRecordsRepository = medicalRecordsRepository;
        this.fireStationRepository = fireStationRepository;
    }

    public List<String> findAllEmailsByCity(String city) {

        return this.personRepository.findAllPersons().stream().filter(p -> p.getCity().equals(city)).map(p -> p.getEmail()).collect(Collectors.toList());
    }

    public List<ChildAlertDto> findAllchildsUnder18ByAddress(String address) {

        List<ChildAlertDto> result = new ArrayList<>();
// récuperer la liste des peronnes habitants à cette adresse

        List<Person> persons = personRepository.findAllpersonByAddress(address);
// recuperer la liste des medical records de - de 18 ans

        List<MedicalRecord> medicalRecords = medicalRecordsRepository.findAllMedicalRecordsUnder18();

// pour chaque élément de personne rechercher dans la liste des - 18 ans
        // je crée une troisieme liste et je fait rentrer les noms qui correspondent
        for (Person person : persons) {
            MedicalRecord medicalRecord = medicalRecordsContainsPerson(medicalRecords, person);
            if (medicalRecord != null) {
                ChildAlertDto dto = new ChildAlertDto();
                dto.setFirstName(person.getFirstName());
                dto.setLastName(person.getLastName());
                dto.setAge(String.valueOf(computeAge(medicalRecord.getBirthdate())));
                dto.setHouseholds(persons.stream().filter(p -> !p.getFirstName().equals(person.getFirstName())).collect(Collectors.toList()));
                result.add(dto);
            }
        }


        return result;
    }

    // recpetionne une personnne et check si elle est dans la liste
    private MedicalRecord medicalRecordsContainsPerson(List<MedicalRecord> medicalRecords, Person person) {
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())) {
                return medicalRecord;
            }
        }
        return null;
    }

    public List<PersonInfoDto> findAllpersonsWithMedicalRecords(String firstName, String lastName) {
        List<PersonInfoDto> result = new ArrayList<>();
// get a list of persons by firstName and lastName
        Person person = personRepository.findpersonByfirstNameAndLastName(firstName, lastName);
// get a list of medical records by firstName ans Lastname
        MedicalRecord medicalRecord = medicalRecordsRepository.findMedicalWithFirstNameAndLastName(firstName, lastName);
// pour chaque élément de personne rechercher dans la liste des - 18 ans
        // je crée une troisieme liste et je fait rentrer les noms qui correspondent

        PersonInfoDto dto = new PersonInfoDto();
        dto.setLastName(person.getLastName());
        dto.setAddress(person.getAdress());
        dto.setAge(String.valueOf(computeAge(medicalRecord.getBirthdate())));
        dto.setEmail(person.getEmail());
        dto.setAllergies(medicalRecord.getAllergies());
        dto.setMedications(medicalRecord.getMedications());
        result.add(dto);


        return result;
    }


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


    public List<FireDto> findAllpersonsWithMedicalRecords(String address) {

        List<FireDto> result = new ArrayList<>();
// get all persons By Address

        List<Person> persons = personRepository.findAllpersonByAddress(address);
// recuperer la liste des medical records de - de 18 ans

        List<MedicalRecord> medicalRecords = medicalRecordsRepository.findAllMedicalRecords();

// pour chaque élément de personne rechercher dans la liste des - 18 ans
        // je crée une troisieme liste et je fait rentrer les noms qui correspondent
        for (Person person : persons) {
            MedicalRecord medicalRecord = medicalRecordsContainsPerson(medicalRecords, person);
            if (medicalRecord != null) {
                FireDto dto = new FireDto();
                FireStation fireStation= fireStationRepository.findFireStationNumberByAddress(address);
                dto.setFireStation(fireStation.getStation());
                dto.setLastName(person.getLastName());
                dto.setPhoneNumber(person.getPhone());
                dto.setAge(String.valueOf(computeAge(medicalRecord.getBirthdate())));
                dto.setMedications(medicalRecord.getMedications());
                dto.setAllergies(medicalRecord.getAllergies());
                result.add(dto);
            }
        }


        return result;
    }
    public void addPerson (Person person){

personRepository.savePerson(person);
    }

    public void updateAPerson(Person person) {

        personRepository.updateAPerson(person);
    }
    public void deletePerson(String firstName,String lastName) {

        personRepository.deleteAPerson(firstName,lastName);
    }
    public List<Person> people()
    {
        return personRepository.findAllPersons();
    }

}




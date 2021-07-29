package com.SafetyNet.Alerts.service;

import com.SafetyNet.Alerts.model.FireStation;
import com.SafetyNet.Alerts.model.MedicalRecord;
import com.SafetyNet.Alerts.model.Person;
import com.SafetyNet.Alerts.repository.FireStationRepository;
import com.SafetyNet.Alerts.repository.MedicalRecordsRepository;
import com.SafetyNet.Alerts.repository.PersonRepository;
import com.SafetyNet.Alerts.service.dto.FireStationDto;
import com.SafetyNet.Alerts.service.dto.FireStationPersonDto;
import com.SafetyNet.Alerts.service.dto.FloodDto;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FireStationService {


    private final FireStationRepository fireStationRepository;
    private final PersonRepository personRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;

    public FireStationService(FireStationRepository fireStationRepository, PersonRepository personRepository, MedicalRecordsRepository medicalRecordsRepository) {
        this.fireStationRepository = fireStationRepository;
        this.personRepository = personRepository;
        this.medicalRecordsRepository = medicalRecordsRepository;
    }

    // get a list of phoneNumbers by station Number
    public List<String> findPhoneNumbersByStationNumber(int number) {

        List<String> result = new ArrayList<>();
// get a fireStationAdress List by  station number
        List<FireStation> fireStations = fireStationRepository.findAllFireStationsAddressByNumber(number);
// get a list of all persons

        List<Person> persons = personRepository.findAllPersons();
//  compares address of fireStation with Peron's address
        // make a third list and put the addresses inside
        for (Person person : persons) {
            if (personsContainsFirestationAddress(fireStations, person)) {
                result.add(person.getPhone());
            }
        }

        return result;

    }


    // get an address on parameter and check if it is in the list
    private boolean personsContainsFirestationAddress(List<FireStation> fireStations, Person person) {
        for (FireStation fireStation : fireStations) {
            if (fireStation.getAddress().equals(person.getAdress())) {
                return true;
            }
        }
        return false;
    }


    public List<FloodDto> flood(List<Integer> stationsNumbers) {

        return stationsNumbers.stream().flatMap(n -> fireStationRepository.findAllFireStationsAddressByNumber(n)
                .stream()).map(s -> FloodDto.builder()
                .address(s.getAddress()).people(getPeopleByAddress(s.getAddress())).build()).collect(Collectors.toList());


    }

    public List<FloodDto.Person> getPeopleByAddress(String address) {


        return personRepository.findAllpersonByAddress(address).stream().map(p -> mapToperson(p)).collect(Collectors.toList());


    }

    private FloodDto.Person mapToperson(Person person) {
        MedicalRecord medicalRecord = medicalRecordsRepository.findMedicalWithFirstNameAndLastName(person.getFirstName(), person.getLastName());
// pour chaque élément de personne rechercher dans la liste des - 18 ans
        // je crée une troisieme liste et je fait rentrer les noms qui correspondent
        return FloodDto.Person.builder()
                .lastName(person.getLastName())
                .phoneNumber(person.getPhone())
                .age(computeAge(medicalRecord.getBirthdate()))
                .allergies(medicalRecord.getAllergies())
                .medications(medicalRecord.getMedications())
                .build();
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

/*
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

 */

    public FireStationDto findAllPersonsByStationNumber(int number) {

        FireStationDto result = new FireStationDto();
        List <FireStationPersonDto> people = new ArrayList<>();
        result.setPeople(people);
        // get all stations by number
        List<FireStation> fireStations = fireStationRepository.findAllFireStationsAddressByNumber(number);
        List<MedicalRecord> medicalRecords = medicalRecordsRepository.findAllMedicalRecords();
        // get all people
        List<Person> persons = personRepository.findAllPersons();
        // compare addresses and add the results in FireSttionDto

        for (Person person : persons) {
            FireStation fireStation = FireStationContainPersons(fireStations, person);
            if (fireStation != null) {
                FireStationPersonDto fireStationPersonDto = new FireStationPersonDto();
                fireStationPersonDto.setFirstName(person.getFirstName());
                fireStationPersonDto.setLastName(person.getLastName());
                fireStationPersonDto.setAddress(person.getAdress());
                fireStationPersonDto.setPhoneNumber(person.getPhone());

                Integer childsCount = 0;
                Integer adultsCount = 0;
                for (Person person2 : persons) {

                    MedicalRecord medicalRecord = medicalRecordsContainsPerson(medicalRecords, person2);
                    if (medicalRecord != null) {
                        if ((computeAge(medicalRecord.getBirthdate()) < 18)) {
                            result.setChildsCount(childsCount + 1);
                            childsCount++;
                        } else
                            result.setAdultsCount(adultsCount + 1);
                        adultsCount++;


                    }
                }
                result.getPeople().add(fireStationPersonDto);
            }


        }
        return result;
    }

    // recpetionne une personnne et check si elle est dans la liste
    private FireStation FireStationContainPersons(List<FireStation> fireStations, Person person) {
        for (FireStation fireStation : fireStations) {
            if (fireStation.getAddress().equals(person.getAdress())) {
                return fireStation;
            }
        }
        return null;
    }

    private MedicalRecord medicalRecordsContainsPerson(List<MedicalRecord> medicalRecords, Person person) {
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())) {
                return medicalRecord;
            }
        }
        return null;
    }
    public void addFireStation (FireStation fireStation){

        fireStationRepository.saveFireStation(fireStation);
    }

    public void updateFireStation(FireStation fireStation) {

        fireStationRepository.updateFireStation(fireStation);
    }
    public void deleteFireStation(String address,String station) {

        fireStationRepository.deleteFireStation(address,station);
    }
    public List<FireStation> allFireStations()
    {
        return fireStationRepository.findAllFireStations();
    }

}



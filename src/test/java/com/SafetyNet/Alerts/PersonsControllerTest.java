package com.SafetyNet.Alerts;

import com.SafetyNet.Alerts.controller.PersonsController;
import com.SafetyNet.Alerts.model.FireStation;
import com.SafetyNet.Alerts.model.Person;
import com.SafetyNet.Alerts.repository.FireStationRepository;
import com.SafetyNet.Alerts.repository.PersonRepository;
import com.SafetyNet.Alerts.service.PersonService;
import com.SafetyNet.Alerts.service.dto.ChildAlertDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class PersonsControllerTest {


    private static List<ChildAlertDto> childs = new ArrayList<>();

    @Autowired
    PersonsController personsController;
    @Autowired
    PersonRepository personRepository;

    @BeforeAll
    private static void setUp() throws Exception {



/*
        List<Person> households = new ArrayList<>();

        Person person2 = new Person("Warren", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7512", "ward@email.com");
        households.add(person);
        households.add(person2);
        ChildAlertDto childAlertDto = new ChildAlertDto("Zach", "Zemicks", "3", households);
        childs.add(childAlertDto);


 */
    }


    @Test
    void listeEMailsTest() {

    Assertions.assertThat(personsController.listeEmails("Culver")).isNotNull();
    }
    @Test
    void childsUnder18ByAddressTest() {


        Assertions.assertThat(personsController.childsUnder18ByAddress("Culver")).isNotNull();
    }

    @Test
    void listOfpersonsWithMedicalRecordsTest() {


Assertions.assertThat(personsController.listOfpersonsWithMedicalRecords("John","Boyd")).isNotNull();
    }


    @Test
    void listOfpersonsByAddressTest() {


        Assertions.assertThat(personsController.listOfpersonsByAddress("1509 Culver St")).isNotNull();
    }


    @Test
    void addAPersonTest() {

        Person person = new Person("firstNameTest", "lastNameTest", "892 Downing Ct", "Culver", "97451", "841-874-7878", "soph@email.com");
        personsController.addAPerson(person);
        Person result = personRepository.findpersonByfirstNameAndLastName(person.getFirstName(), person.getLastName());
        assert (result.getFirstName().equals(person.getFirstName()));
        assert (result.getLastName().equals(person.getLastName()));
    }

    @Test
    void updatePersontest() {
        Person person = new Person("firstNameTest2", "lastNameTest2", "test", "Culver", "97451", "841-874-7878", "soph@email.com");
        personRepository.savePerson(person);
        Person personUpdate = new Person("firstNameTest2", "lastNameTest2", "testUpdated", "Culver", "97451", "841-874-7878", "soph@email.com");
       personsController.updateAPerson(personUpdate);
        Person result = personRepository.findpersonByfirstNameAndLastName(personUpdate.getFirstName(), personUpdate.getLastName());
        assert (result.getAdress().equals(personUpdate.getAdress()));


    }

    @Test
    void deleteFireStationtest() {
        Person person = new Person("firstNameTest4", "lastNameTest4", "892 Downing Ct", "Culver", "97451", "841-874-7878", "soph@email.com");
        personsController.addAPerson(person);
        personsController.deletePersoneAPerson("firstNameTest4", "lastNameTest4");
        Person result = personRepository.findpersonByfirstNameAndLastName(person.getFirstName(), person.getLastName());
        Assert.isNull(result.getAdress(), "person does not exist");
    }


    @Test
    void allFireStationtest() {
        List<Person> people = personsController.allpeople();

        Assert.notNull(people, "people is not null ");

    }







}

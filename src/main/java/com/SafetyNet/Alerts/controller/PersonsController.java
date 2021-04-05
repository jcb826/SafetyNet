package com.SafetyNet.Alerts.controller;


import com.SafetyNet.Alerts.model.FireStation;
import com.SafetyNet.Alerts.model.MedicalRecord;
import com.SafetyNet.Alerts.model.Person;
import com.SafetyNet.Alerts.service.PersonService;
import com.SafetyNet.Alerts.service.dto.ChildAlertDto;
import com.SafetyNet.Alerts.service.dto.FireDto;
import com.SafetyNet.Alerts.service.dto.PersonInfoDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonsController {
    private final PersonService personService;

    PersonsController(PersonService personService) {
        this.personService = personService;
    }

    //Get an email list
    @RequestMapping(value = "communityEmail", method = RequestMethod.GET)
    public List<String> listeEmails(@RequestParam(name = "city") String city) {

        return this.personService.findAllEmailsByCity(city);

    }

    @RequestMapping(value = "childAlert", method = RequestMethod.GET)
    public List<ChildAlertDto> childsUnder18ByAddress(@RequestParam(name = "address") String address) {

        return this.personService.findAllchildsUnder18ByAddress(address);

    }


    @RequestMapping(value = "personInfo", method = RequestMethod.GET)
    public List<PersonInfoDto> listOfpersonsWithMedicalRecords(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {

        return this.personService.findAllpersonsWithMedicalRecords(firstName, lastName);

    }


    @RequestMapping(value = "fire", method = RequestMethod.GET)
    public List<FireDto> listOfpersonsByAddress(@RequestParam(name = "address") String address) {

        return this.personService.findAllpersonsWithMedicalRecords(address);

    }

    // add a person
    @PostMapping(value = "person")
    public void addAPerson(@RequestBody Person person) {
        personService.addPerson(person);
    }

    // update a person
    @PutMapping(value = "person")
    public void updateAPerson(@RequestBody Person person) {
        personService.updateAPerson(person);
    }

    // delete a person
    @DeleteMapping(value = "person")
    public void deletePersoneAPerson(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        personService.deletePerson(firstName, lastName);
    }
    @RequestMapping(value = "person", method = RequestMethod.GET)
    public List<Person> allpeople() {

        return this.personService.people();
    }

}
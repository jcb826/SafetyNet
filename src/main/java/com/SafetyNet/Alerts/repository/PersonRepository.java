package com.SafetyNet.Alerts.repository;

import com.SafetyNet.Alerts.model.MedicalRecord;
import com.SafetyNet.Alerts.model.Person;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonRepository {

    private final DataHandler dataHandler;

    public PersonRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public List<Person> findAllPersons() {

        return dataHandler.getData().getPersons();
    }
    public List<Person> findAllpersonByAddress(String address){

        return dataHandler.getData().getPersons().stream().filter(p->p.getAdress().equals(address)).collect(Collectors.toList());
    }

    public Person findpersonByfirstNameAndLastName(String firstName, String lastName){

        return dataHandler.getData().getPersons().stream()
                .filter(p->p.getFirstName().equals(firstName))
                .filter(p->p.getLastName().equals(lastName))
                .findFirst()
                .orElseGet(()->new Person());
    }

public void savePerson (Person person){
        dataHandler.getData().getPersons().add(person);
        dataHandler.save();
}

    public void updateAPerson (Person person){

        List<Person> people = dataHandler.getData().getPersons();
        for (Person person2 : people) {

            if (person2.getLastName().equals((person.getLastName()))&&person2.getFirstName().equals(person.getFirstName())) {

                person2.setAddress(person.getAdress());
                person2.setCity(person.getCity());
                person2.setEmail(person.getEmail());
                person2.setPhone(person.getPhone());
                person2.setZip(person.getZip());
            }
        }


        // look for the person to update

        // set the new properties of this person
        dataHandler.getData().setPersons(people);
        dataHandler.save();
    }

    public void deleteAPerson(String firstName, String lastName) {

        List<Person> people = dataHandler.getData().getPersons();
        Integer count = null;
        for (int i = 0; i < people.size(); i++) {

            if (people.get(i).getFirstName().equals(firstName) && people.get(i).getLastName().equals(lastName)) {
                count = i;

            }


        }
        if (count != null ){
            people.remove(count.intValue());
            dataHandler.getData().setPersons(people);
            dataHandler.save();
        }
    }


}

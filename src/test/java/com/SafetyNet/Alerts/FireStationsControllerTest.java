package com.SafetyNet.Alerts;

import com.SafetyNet.Alerts.controller.FireStationsController;
import com.SafetyNet.Alerts.model.FireStation;
import com.SafetyNet.Alerts.model.Person;
import com.SafetyNet.Alerts.repository.FireStationRepository;
import com.SafetyNet.Alerts.service.dto.ChildAlertDto;
import com.SafetyNet.Alerts.service.dto.FireStationDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class FireStationsControllerTest {

    private static List<String> phoneNumbers = new ArrayList<>();

    @Autowired
    FireStationsController fireStationsController;
    @Autowired
    FireStationRepository fireStationRepository;

    @BeforeAll
    private static void setUp() throws Exception {
        phoneNumbers.add("841-874-6874");
        phoneNumbers.add("841-874-9845");
        phoneNumbers.add("841-874-8888");
        phoneNumbers.add("841-874-9888");


        List<Person> households = new ArrayList<>();
        Person person = new Person("Sophia", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7878", "soph@email.com");
        Person person2 = new Person("Warren", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7512", "ward@email.com");
        households.add(person);
        households.add(person2);
        ChildAlertDto childAlertDto = new ChildAlertDto("Zach", "Zemicks", "3", households);


    }

    @Test
    void phoneNumberListTest() {

        assert (fireStationsController.phoneNumberList(4).equals(phoneNumbers));
    }

    @Test
    void personsListByFireStationTest() {
        FireStationDto result = fireStationsController.personsListByFireStation(2);
        assert (result.getPeople().get(0).getFirstName().contains("Jonanathan"));
    }

    @Test
    void floodTest() {
        List<Integer> stationsList = new ArrayList<>();
        stationsList.add(1);
        stationsList.add(2);
        Assert.notNull(fireStationsController.flood(stationsList), "Stations List Result is not null");
    }

    @Test
    void addFireStationTest() {

        FireStation fireStation = new FireStation("test", "56");
        fireStationsController.addAFirestation(fireStation);
        FireStation result = fireStationRepository.findFireStationNumberByAddressAndNumber(fireStation.getAddress(), fireStation.getStation());
        assert (result.getAddress().equals(fireStation.getAddress()));
        assert (result.getStation().equals(fireStation.getStation()));
    }

    @Test
    void updateFireStationtest() {
        FireStation fireStation = new FireStation("test1", "57");
        fireStationsController.addAFirestation(fireStation);
        FireStation fireStationUpdate = new FireStation("test1", "58");
        fireStationsController.updateAFirestation(fireStationUpdate);
        FireStation result = fireStationRepository.findFireStationNumberByAddressAndNumber(fireStationUpdate.getAddress(), fireStationUpdate.getStation());
        assert (result.getStation().equals(fireStationUpdate.getStation()));
        assert (result.getAddress().equals(fireStationUpdate.getAddress()));

    }


    @Test
    void deleteFireStationtest() {
        FireStation fireStation = new FireStation("test2", "58");
        fireStationsController.addAFirestation(fireStation);
        fireStationsController.deleteAFireStation("test2", "58");
        FireStation result = fireStationRepository.findFireStationNumberByAddressAndNumber(fireStation.getAddress(), fireStation.getStation());
        Assert.isNull(result.getAddress(), "fireStation does not exist");
    }


    @Test
    void allFireStationtest() {
        List<FireStation> fireStations = fireStationsController.allFireStations();

        Assert.notNull(fireStations, "fireStations is not null ");

    }

}

package com.SafetyNet.Alerts.controller;


import com.SafetyNet.Alerts.model.FireStation;
import com.SafetyNet.Alerts.service.FireStationService;
import com.SafetyNet.Alerts.service.dto.FireStationDto;
import com.SafetyNet.Alerts.service.dto.FloodDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FireStationsController {


    private final FireStationService fireStationService;

    public FireStationsController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    //get the phones numbers by fireStations
    @RequestMapping(value = "phoneAlert", method = RequestMethod.GET)
    public List<String> phoneNumberList(@RequestParam(name = "fireStation") int number) {
        return this.fireStationService.findPhoneNumbersByStationNumber(number);
    }

    //get the persons By fireStations will give first name, last name address phone number, and must give also the number of adults and childs (+ ou- 18 years old )
    @RequestMapping(value = "fireStation", method = RequestMethod.GET)
    public FireStationDto personsListByFireStation(@RequestParam(name = "stationNumber") int number) {

        return this.fireStationService.findAllPersonsByStationNumber(number);
    }
    
    @RequestMapping(value = "flood/stations", method = RequestMethod.GET)
    public List<FloodDto> flood(@RequestParam(name = "stations") List<Integer> numbers) {
        return this.fireStationService.flood(numbers);
    }

    // add a fireStation
    @PostMapping(value = "firestation")
    public void addAFirestation(@RequestBody FireStation fireStation) {
        fireStationService.addFireStation(fireStation);
    }

    // update a fireStation
    @PutMapping(value = "firestation")
    public void updateAFirestation(@RequestBody FireStation fireStation) {
        fireStationService.updateFireStation(fireStation);
    }

    // delete a firestation
    @DeleteMapping(value = "firestation")
    public void deleteAFireStation(@RequestParam(name = "address") String address, @RequestParam(name = "station") String station) {
        fireStationService.deleteFireStation(address, station);
    }

    //get all fireStations
    @RequestMapping(value = "firestation", method = RequestMethod.GET)
    public List<FireStation> allFireStations() {

        return this.fireStationService.allFireStations();
    }

}

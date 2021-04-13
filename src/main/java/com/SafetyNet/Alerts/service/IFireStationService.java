package com.SafetyNet.Alerts.service;

import com.SafetyNet.Alerts.model.FireStation;
import com.SafetyNet.Alerts.service.dto.FireStationDto;
import com.SafetyNet.Alerts.service.dto.FloodDto;

import java.util.List;


public interface IFireStationService {


    // get a list of phoneNumbers by station Number
    List<String> findPhoneNumbersByStationNumber(int number);


    List<FloodDto> flood(List<Integer> stationsNumbers);

    List<FloodDto.Person> getPeopleByAddress(String address);

    FireStationDto findAllPersonsByStationNumber(int number);


    void addFireStation(FireStation fireStation);


    public void updateFireStation(FireStation fireStation);

    public void deleteFireStation(String address, String station);

    public List<FireStation> allFireStations();

}



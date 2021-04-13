package com.SafetyNet.Alerts.service;

import com.SafetyNet.Alerts.model.FireStation;
import com.SafetyNet.Alerts.service.dto.FireStationDto;
import com.SafetyNet.Alerts.service.dto.FloodDto;

import java.util.List;


public interface IFireStationService {
    
    List<String> findPhoneNumbersByStationNumber(int number);

    List<FloodDto> flood(List<Integer> stationsNumbers);

    List<FloodDto.Person> getPeopleByAddress(String address);

    FireStationDto findAllPersonsByStationNumber(int number);

    void addFireStation(FireStation fireStation);

    void updateFireStation(FireStation fireStation);

    void deleteFireStation(String address, String station);

    List<FireStation> allFireStations();

}



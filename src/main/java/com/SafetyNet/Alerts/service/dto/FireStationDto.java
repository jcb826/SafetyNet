package com.SafetyNet.Alerts.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class FireStationDto {


    Integer adultsCount;
    Integer childsCount;
    List<FireStationPersonDto> people;


}

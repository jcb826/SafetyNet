package com.SafetyNet.Alerts.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class FloodDto {

    String address;
    List<Person> people;

    @Builder
    @Data
    public static class Person {
        String lastName;
        String phoneNumber;
        Integer age;
        private String[] medications;
        private String[] allergies;
    }
}

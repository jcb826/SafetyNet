package com.SafetyNet.Alerts.service.dto;

import com.SafetyNet.Alerts.model.MedicalRecord;

import java.util.List;

public class PersonInfoDto {



    String lastName;
    String email;
    String age;
    String address;

    private String[] medications;
    private String[] allergies;

    public String[] getMedications() {
        return medications;
    }

    public void setMedications(String[] medications) {
        this.medications = medications;
    }

    public String[] getAllergies() {
        return allergies;
    }

    public void setAllergies(String[] allergies) {
        this.allergies = allergies;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }



    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}

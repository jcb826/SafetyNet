package com.SafetyNet.Alerts.repository;

import com.SafetyNet.Alerts.model.FireStation;
import com.SafetyNet.Alerts.model.MedicalRecord;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FireStationRepository {

    private final DataHandler dataHandler;

    public FireStationRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public List<FireStation> findAllFireStations() {

        return dataHandler.getData().getFirestations();
    }

    public List<FireStation> findAllFireStationsAddressByNumber(Integer number) {

        return dataHandler.getData().getFirestations().stream().filter(f -> f.getStation().equals(number.toString())).collect(Collectors.toList());
    }

    public FireStation findFireStationNumberByAddress(String address) {
        return dataHandler.getData().getFirestations().stream()

                .filter(p -> p.getAddress().equals(address))
                .findFirst()
                .orElseGet(() -> new FireStation());

    }

    public MedicalRecord findMedicalWithFirstNameAndLastName(String firstName, String lastName) {
        return dataHandler.getData().getMedicalrecords().stream()
                .filter(p -> p.getFirstName().equals(firstName))
                .filter(p -> p.getLastName().equals(lastName))
                .findFirst()
                .orElseGet(() -> new MedicalRecord());

    }

    public List<FireStation> findAllFireStationsAddressByAListOfNumbers(List<Integer> numbers) {
        List<FireStation> result = new ArrayList<>();
        for (int number : numbers) {
            dataHandler.getData().getFirestations().stream().filter(p -> p.getStation().equals(number)).collect(Collectors.toList());
        }

        return result;
    }


    public void saveFireStation(FireStation fireStation) {
        dataHandler.getData().getFirestations().add(fireStation);
        dataHandler.save();
    }

    public void updateFireStation(FireStation fireStation) {

        List<FireStation> fireStations = dataHandler.getData().getFirestations();
        for (FireStation fireStation2 : fireStations) {

            if (fireStation2.getAddress().equals((fireStation.getAddress()))) {

                fireStation2.setStation(fireStation.getStation());

            }
        }


        // look for the person to update

        // set the new properties of fireStation
        dataHandler.getData().setFirestations(fireStations);
        dataHandler.save();
    }

    public void deleteFireStation(String address, String station) {

        List<FireStation> fireStations = dataHandler.getData().getFirestations();
        Integer count = null;
        for (int i = 0; i < fireStations.size(); i++) {

            if (fireStations.get(i).getAddress().equals(address) && fireStations.get(i).getStation().equals(station)) {
                count = i;

            }


        }
        if (count != null) {
            fireStations.remove(count.intValue());
            dataHandler.getData().setFirestations(fireStations);
            dataHandler.save();
        }
    }

    /*
        public FireStation getFireStation(FireStation fireStation) {
            FireStation result = new FireStation();
            List<FireStation> fireStations = dataHandler.getData().getFirestations();
            for (FireStation fireStation2 : fireStations) {

                if (fireStation2.getAddress().equals((fireStation.getAddress())) && fireStation2.getStation().equals((fireStation.getStation()))) {

                    result = fireStation2;
                    break;
                }

            }
            return result;
        }
        */
    public FireStation findFireStationNumberByAddressAndNumber(String address, String station) {
        return dataHandler.getData().getFirestations().stream()

                .filter(p -> p.getAddress().equals(address))
                .filter(p -> p.getStation().equals(station))
                .findFirst()
                .orElseGet(() -> new FireStation());

    }

}






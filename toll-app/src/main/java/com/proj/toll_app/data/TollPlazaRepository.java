package com.proj.toll_app.data;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.proj.toll_app.model.TollPlaza;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class TollPlazaRepository {

    private final List<TollPlaza> tollPlazas = new ArrayList<>();

    // This method will be executed after the bean is initialized
    @PostConstruct
    public void loadTollPlazaData() throws IOException, CsvException {
        // Assuming the CSV file is in src/main/resources
        try (CSVReader reader = new CSVReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("toll_plaza_list.csv"))))) {
            
            // Skip header row
            reader.skip(1); 
            List<String[]> records = reader.readAll();
            
            for (String[] record : records) {
                // Assuming CSV columns: Name, Latitude, Longitude
                String name = record[0];
                double latitude = Double.parseDouble(record[1]);
                double longitude = Double.parseDouble(record[2]);
                tollPlazas.add(new TollPlaza(name, latitude, longitude));
            }
        }
    }

    public List<TollPlaza> findAll() {
        return new ArrayList<>(tollPlazas);
    }
}
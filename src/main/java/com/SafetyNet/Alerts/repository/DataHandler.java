package com.SafetyNet.Alerts.repository;

import com.SafetyNet.Alerts.model.Data;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Component
public class DataHandler {


    private final Data data;


    public DataHandler() throws IOException {
        String temp = getFromResource("data.json");
        this.data = JsonIterator.deserialize(temp, Data.class);
    }

    private String getFromResource(String s) throws IOException {
        InputStream is = new ClassPathResource(s).getInputStream();
        return IOUtils.toString(is, StandardCharsets.UTF_8);
    }

    public Data getData() {
        return data;
    }

    public void save() {
        String json = JsonStream.serialize(data);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("classpath:data.json"));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

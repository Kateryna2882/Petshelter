package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Animal;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AnimalSerializer {
    private final ObjectMapper mapper;
    private final String format;

    public AnimalSerializer(ObjectMapper mapper, String format) {
        this.mapper = mapper;
        this.format = format;
    }

    public void serialize(Animal animal) {

        try {
            createDirectories();
            mapper.writeValue(new File("src/main/resources/animal."), animal);
        } catch (IOException e) {
            System.err.println("Error creating file or writing data: " + e.getMessage());
        }
    }

    public void serialize(List<Animal> animals) {
        try {
            createDirectories();
            mapper.writeValue(new File("src/main/resources/animal."), animals);
        } catch (IOException e) {
            System.err.println("Error creating file or writing data: " + e.getMessage());
        }
    }

    public Optional<List<Animal>> deserialize() {
        try {
            Animal[] animalsArray = mapper.readValue(new File("src/main/resources/animal."), Animal[].class);
            return Optional.of(Arrays.asList(animalsArray));
        } catch (IOException e) {
            System.err.println("Error reading file or parsing data: " + e.getMessage());
            return Optional.empty();
        }
    }

    private void createDirectories() {
        File file = new File("src/main/resources/animal.");
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                System.err.println("Error creating directories: " + parentDir.getAbsolutePath());
            }
        }
    }
}


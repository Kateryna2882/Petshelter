package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PetShelterAddTest {
    private org.example.model.PetShelterAdd petShelter;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }


    @BeforeEach
    public void setUp() {
        petShelter = new org.example.model.PetShelterAdd("test_pet_shelter_data.json");
    }

    @Test
    public void testAddPet() {
        Animal testAnimal = new Animal("TestName", "TestBreed", 3);

        petShelter.addPet(testAnimal);

        assertTrue(petShelter.getAnimals().contains(testAnimal));
    }

    @Test
    public void testRemovePet() {
        Animal testAnimal = new Animal("TestName", "TestBreed", 3);

        petShelter.addPet(testAnimal);

        petShelter.removePet(testAnimal);

        Assertions.assertFalse(petShelter.getAnimals().contains(testAnimal));
    }

    @Test
    public void testAddPetWithScannerInput() {

        ByteArrayInputStream in = new ByteArrayInputStream("TestName\nTestBreed\n3\n".getBytes());
        System.setIn(in);

        petShelter.addPet();
        System.setIn(System.in);

        List<Animal> animals = petShelter.getAnimals();
        Assertions.assertFalse(animals.isEmpty());
        assertEquals("TestName", animals.get(0).getName());
        assertEquals("TestBreed", animals.get(0).getBreed());
        assertEquals(3, animals.get(0).getAge());
    }

    @Test
    void testSaveAndLoadAnimalsToFile() throws IOException {
        Animal testAnimal = new Animal("TestName", "TestBreed", 3);

        petShelter.addPet(testAnimal);

        petShelter.saveAnimalsToFileForTest();

        PetShelterAdd newPetShelter = new PetShelterAdd("test_pet_shelter_data.json");
        assertTrue(newPetShelter.getAnimals().stream()
                .anyMatch(animal ->
                        animal.getName().equals(testAnimal.getName()) &&
                                animal.getBreed().equals(testAnimal.getBreed()) &&
                                animal.getAge() == testAnimal.getAge()
                ));
        File testFile = new File("test_pet_shelter_data.json");
        assertTrue(testFile.exists());

        Files.deleteIfExists(Path.of("test_pet_shelter_data.json"));
    }


    @Test
    public void testSaveAnimalsToFile() throws IOException {
        Animal testAnimal = new Animal("TestName", "TestBreed", 3);
        petShelter.addPet(testAnimal);
        petShelter.saveAnimalsToFileForTest();
        PetShelterAdd newPetShelter = new PetShelterAdd("test_pet_shelter_data.json");
        assertTrue(newPetShelter.getAnimals().contains(testAnimal));
        Files.deleteIfExists(Path.of("test_pet_shelter_data.json"));
    }

    @Test
    public void testSearchPetByBreed() {
        org.example.model.PetShelterAdd petShelterAdd = new org.example.model.PetShelterAdd();
        Animal dog = new Animal("Dog1", "Dog", 2);
        Animal cat1 = new Animal("Cat1", "Cat", 1);
        Animal cat2 = new Animal("Cat2", "Cat", 3);

        petShelterAdd.addPet(dog);
        petShelterAdd.addPet(cat1);
        petShelterAdd.addPet(cat2);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ByteArrayInputStream in = new ByteArrayInputStream("Cat\n".getBytes());
        System.setIn(in);

        petShelterAdd.searchPetByBreed();

        System.setIn(System.in);
        System.setOut(System.out);


    }


}

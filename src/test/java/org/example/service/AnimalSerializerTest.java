package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AnimalSerializerTest {

    private AnimalSerializer animaXmlSerializer =
            new AnimalSerializer(new ObjectMapper(), ".json");
    private final Animal dog = new Animal("Funny", "shitcy", 1);
    private final Animal cat = new Animal("Tima", "nobreed", 15);

    @Test
    public void serializeTest() {
        animaXmlSerializer.serialize(dog);
    }
@Test
public void serializeListTest(){
        animaXmlSerializer.serialize(List.of(dog, cat));
}
@Test
public void deserializeTest(){
        Optional<List<Animal> >deserialize = animaXmlSerializer.deserialize();

    }
}



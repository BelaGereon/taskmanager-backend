package com.example.taskmanager.utility;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.junit.jupiter.api.Test;

import static com.example.taskmanager.utility.JsonUtils.toJson;
import static org.junit.jupiter.api.Assertions.*;

class JsonUtilsTest {

    static class Sample {
        public String name;
        public int value;

        public Sample(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }

    static class NotSerializable {
        // Jackson cannot serialize this field
        @JsonIgnore
        public Runnable callback = () -> {};
    }

    @Test
    void givenSerializableObject_whenConvertingToJson_thenReturnValidJson() {
        Sample sample = new Sample("Test", 42);

        String json = toJson(sample);

        assertTrue(json.contains("\"name\":\"Test\""));
        assertTrue(json.contains("\"value\":42"));
    }

    @Test
    void givenNotSerializableObject_whenConvertingToJson_thenThrowRuntimeException() {
        NotSerializable notSerializable = new NotSerializable();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            toJson(notSerializable);
        });

        assertTrue(exception.getMessage().contains("Failed to convert object to JSON"));
    }

    @Test
    void givenNullObject_whenConvertingToJson_thenReturnJsonNull() {
        String json = toJson(null);

        assertEquals("null", json);
    }

}

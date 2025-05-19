package com.bkg.vetflux_assignment.config.jpa;

import java.io.IOException;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;

public class SetConverter<T> implements AttributeConverter<Set<T>, String> {

    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

    @Override
    public String convertToDatabaseColumn(Set<T> attribute) {
        try {
            return attribute != null && !attribute.isEmpty() ? mapper.writeValueAsString(attribute) : null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Set<T> convertToEntityAttribute(String dbData) {
        try {
            return dbData != null && !dbData.isBlank() ? mapper.readValue(dbData, new TypeReference<Set<T>>() {}) : null;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}

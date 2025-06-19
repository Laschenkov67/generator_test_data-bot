package com.example.javabot;
import com.example.javabot.service.GuidUuidGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class GuidUuidGeneratorServiceIntegrationTest {

    private GuidUuidGeneratorService service;

    private static final Pattern GUID_PATTERN = Pattern.compile(
            "^[8-9A-F][0-9A-F]{7}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}$"
    );

    @BeforeEach
    void setUp() {
        service = new GuidUuidGeneratorService();
        service.init();
    }

    @RepeatedTest(100)
    void testGenerateGuid_shouldAlwaysReturnValidFormat() {
        String guid = service.generateGuid();
        assertTrue(GUID_PATTERN.matcher(guid).matches(),
                "Неверный формат GUID: " + guid);
    }

    @Test
    void testUniqueness_shouldGenerateUniqueValues() {
        Set<String> guids = new HashSet<>();
        Set<String> uuids = new HashSet<>();

        int iterations = 1000;

        for (int i = 0; i < iterations; i++) {
            guids.add(service.generateGuid());
            uuids.add(service.generateUuid());
        }

        // Проверяем, что большинство сгенерированных значений уникальны
        assertTrue(guids.size() > iterations * 0.95,
                "Должно быть сгенерировано более 95% уникальных GUID");
        assertTrue(uuids.size() > iterations * 0.95,
                "Должно быть сгенерировано более 95% уникальных UUID");
    }
}

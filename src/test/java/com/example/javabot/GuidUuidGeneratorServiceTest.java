package com.example.javabot;
import com.example.javabot.service.GuidUuidGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GuidUuidGeneratorServiceTest {

    @Mock
    private Random random;

    @InjectMocks
    private GuidUuidGeneratorService service;

    private static final Pattern GUID_PATTERN = Pattern.compile(
            "^[8-9A-F][0-9A-F]{7}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}$"
    );

    private static final Pattern GUID_LOWER_PATTERN = Pattern.compile(
            "^[8-9a-f][0-9a-f]{7}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"
    );

    private static final Pattern UUID_PATTERN = Pattern.compile(
            "^[0-9a-f]{32}$"
    );

    @BeforeEach
    void setUp() {
        service.init();
    }

    @Test
    void testGenerateGuid_shouldReturnValidGuid() {
        // Arrange
        when(random.nextInt(anyInt())).thenReturn(0);

        // Act
        String guid = service.generateGuid();

        // Assert
        assertNotNull(guid);
        assertEquals(36, guid.length());
        assertTrue(GUID_PATTERN.matcher(guid).matches(),
                "GUID должен соответствовать формату: " + guid);
        assertEquals(guid.toUpperCase(), guid,
                "GUID должен быть в верхнем регистре");
    }

    @Test
    void testGenerateGuid_shouldStartWithCorrectCharacter() {
        // Arrange
        when(random.nextInt(8)).thenReturn(2); // Вернёт 'A' для первого символа
        when(random.nextInt(16)).thenReturn(0); // Вернёт '0' для остальных

        // Act
        String guid = service.generateGuid();

        // Assert
        assertTrue(guid.startsWith("A"),
                "GUID должен начинаться с одного из символов [8-9A-F]");
    }

    @Test
    void testGenerateGuidLower_shouldReturnValidGuidInLowerCase() {
        // Arrange
        when(random.nextInt(anyInt())).thenReturn(0);

        // Act
        String guid = service.generateGuidLower();

        // Assert
        assertNotNull(guid);
        assertEquals(36, guid.length());
        assertTrue(GUID_LOWER_PATTERN.matcher(guid).matches(),
                "GUID должен соответствовать формату в нижнем регистре: " + guid);
        assertEquals(guid.toLowerCase(), guid,
                "GUID должен быть в нижнем регистре");
    }

    @Test
    void testGenerateGuidLower_shouldHaveCorrectFormat() {
        // Arrange
        when(random.nextInt(anyInt())).thenReturn(3);

        // Act
        String guid = service.generateGuidLower();

        // Assert
        String[] parts = guid.split("-");
        assertEquals(5, parts.length);
        assertEquals(8, parts[0].length());
        assertEquals(4, parts[1].length());
        assertEquals(4, parts[2].length());
        assertEquals(4, parts[3].length());
        assertEquals(12, parts[4].length());
    }

    @Test
    void testGenerateUuid_shouldReturnValidUuid() {
        // Arrange
        when(random.nextInt(anyInt())).thenReturn(5);

        // Act
        String uuid = service.generateUuid();

        // Assert
        assertNotNull(uuid);
        assertEquals(32, uuid.length());
        assertTrue(UUID_PATTERN.matcher(uuid).matches(),
                "UUID должен содержать только hex символы в нижнем регистре: " + uuid);
    }

    @Test
    void testGenerateUuid_shouldUseAllHexChars() {
        // Arrange
        when(random.nextInt(16))
                .thenReturn(0).thenReturn(1).thenReturn(2).thenReturn(3)
                .thenReturn(4).thenReturn(5).thenReturn(6).thenReturn(7)
                .thenReturn(8).thenReturn(9).thenReturn(10).thenReturn(11)
                .thenReturn(12).thenReturn(13).thenReturn(14).thenReturn(15)
                .thenReturn(0).thenReturn(1).thenReturn(2).thenReturn(3)
                .thenReturn(4).thenReturn(5).thenReturn(6).thenReturn(7)
                .thenReturn(8).thenReturn(9).thenReturn(10).thenReturn(11)
                .thenReturn(12).thenReturn(13).thenReturn(14).thenReturn(15);

        // Act
        String uuid = service.generateUuid();

        // Assert
        assertEquals("0123456789abcdef0123456789abcdef", uuid);
    }

    @Test
    void testMultipleGenerations_shouldProduceDifferentResults() {
        // Arrange
        when(random.nextInt(anyInt()))
                .thenReturn(0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7)
                .thenReturn(7, 6, 5, 4, 3, 2, 1, 0, 7, 6, 5, 4, 3, 2, 1, 0);

        // Act
        String guid1 = service.generateGuid();
        String guid2 = service.generateGuid();

        // Assert
        assertNotEquals(guid1, guid2,
                "Последовательные вызовы должны генерировать разные GUID");
    }

    @Test
    void testInit_shouldPopulateHexData() {
        // Тест проверяет, что метод init() корректно инициализирует данные
        // Поскольку метод уже вызван в @BeforeEach, просто проверим результат

        // Act
        String guid = service.generateGuid();
        String guidLower = service.generateGuidLower();
        String uuid = service.generateUuid();

        // Assert
        assertNotNull(guid);
        assertNotNull(guidLower);
        assertNotNull(uuid);
    }
}

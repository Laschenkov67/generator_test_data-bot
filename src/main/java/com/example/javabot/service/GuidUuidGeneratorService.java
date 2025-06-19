package com.example.javabot.service;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GuidUuidGeneratorService {
    private static final Map<String, List<String>> HEX_DATA = new HashMap<>();
    private final Random random = new Random();

    @PostConstruct
    public void init() {
        HEX_DATA.put("guidFirstChars", Arrays.asList("8", "9", "A", "B", "C", "D", "E", "F"));
        HEX_DATA.put("guidFirstCharsLower", Arrays.asList("8", "9", "a", "b", "c", "d", "e", "f"));
        HEX_DATA.put("allHexChars", Arrays.asList(
                "0", "1", "2", "3", "4", "5", "6", "7",
                "8", "9", "a", "b", "c", "d", "e", "f"
        ));
    }

    /**
     * Генерация GUID в формате XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX
     * Пример: FAC82352-032B-488D-ACF3-A7D42ABADB8B
     */
    public String generateGuid() {
        return String.format("%s%s%s%s-%s%s%s%s-%s%s%s%s-%s%s%s%s-%s%s%s%s%s%s%s%s",
                getRandomHex(true), getRandomHex(true), getRandomHex(true), getRandomHex(true),
                getRandomHex(true), getRandomHex(true), getRandomHex(true), getRandomHex(true),
                getRandomHex(false), getRandomHex(false), getRandomHex(false), getRandomHex(false),
                getRandomHex(false), getRandomHex(false), getRandomHex(false), getRandomHex(false),
                getRandomHex(false), getRandomHex(false), getRandomHex(false), getRandomHex(false),
                getRandomHex(false), getRandomHex(false), getRandomHex(false), getRandomHex(false)
        ).toUpperCase();
    }

    /**
     * Генерация GUID в нижнем регистре в формате xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
     * Пример: f928c63e-6246-4e06-b0cb-3bc7b708aed9
     */
    public String generateGuidLower() {
        return String.format("%s%s%s%s%s%s%s%s-%s%s%s%s-%s%s%s%s-%s%s%s%s-%s%s%s%s%s%s%s%s%s%s%s%s",
                getRandomHexLower(true), getRandomHexLower(false), getRandomHexLower(false), getRandomHexLower(false),
                getRandomHexLower(false), getRandomHexLower(false), getRandomHexLower(false), getRandomHexLower(false),
                getRandomHexLower(false), getRandomHexLower(false), getRandomHexLower(false), getRandomHexLower(false),
                getRandomHexLower(false), getRandomHexLower(false), getRandomHexLower(false), getRandomHexLower(false),
                getRandomHexLower(false), getRandomHexLower(false), getRandomHexLower(false), getRandomHexLower(false),
                getRandomHexLower(false), getRandomHexLower(false), getRandomHexLower(false), getRandomHexLower(false),
                getRandomHexLower(false), getRandomHexLower(false), getRandomHexLower(false), getRandomHexLower(false),
                getRandomHexLower(false), getRandomHexLower(false), getRandomHexLower(false), getRandomHexLower(false)
        );
    }

    /**
     * Генерация UUID в формате xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
     * Пример: d0e12179d0fa463eb77895a08d9f4ac5
     */
    public String generateUuid() {
        StringBuilder uuid = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            uuid.append(getRandomHex(false));
        }
        return uuid.toString();
    }

    private String getRandomHex(boolean forGuidStart) {
        List<String> source = forGuidStart ?
                HEX_DATA.get("guidFirstChars") :
                HEX_DATA.get("allHexChars");
        return source.get(random.nextInt(source.size()));
    }

    private String getRandomHexLower(boolean forGuidStart) {
        List<String> source = forGuidStart ?
                HEX_DATA.get("guidFirstCharsLower") :
                HEX_DATA.get("allHexChars");
        return source.get(random.nextInt(source.size()));
    }
}

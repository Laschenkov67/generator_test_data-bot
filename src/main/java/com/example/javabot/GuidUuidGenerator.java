package com.example.javabot;

import java.util.*;

public class GuidUuidGenerator {
    private static final Map<String, List<String>> HEX_DATA = new HashMap<>();
    private static final Random random = new Random();

    static {
        // Инициализация hex-символов
        HEX_DATA.put("guidFirstChars", Arrays.asList("8", "9", "A", "B", "C", "D", "E", "F"));
        HEX_DATA.put("allHexChars", Arrays.asList(
                "0", "1", "2", "3", "4", "5", "6", "7",
                "8", "9", "a", "b", "c", "d", "e", "f"
        ));
    }

    /**
     * Генерация GUID в формате XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX
     * Пример: FAC82352-032B-488D-ACF3-A7D42ABADB8B
     */
    public static String generateGuid() {
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
     * Генерация UUID в формате xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
     * Пример: d0e12179d0fa463eb77895a08d9f4ac5
     */
    public static String generateUuid() {
        StringBuilder uuid = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            uuid.append(getRandomHex(false));
        }
        return uuid.toString();
    }

    private static String getRandomHex(boolean forGuidStart) {
        List<String> source = forGuidStart ?
                HEX_DATA.get("guidFirstChars") :
                HEX_DATA.get("allHexChars");
        return source.get(random.nextInt(source.size()));
    }

    /**
     * Генерация обоих идентификаторов
     */
    public static Map<String, String> generateBoth() {
        Map<String, String> ids = new LinkedHashMap<>();
        ids.put("GUID", generateGuid());
        ids.put("UUID", generateUuid());
        return ids;
    }
}

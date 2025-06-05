package com.example.javabot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class PersonDataGenerator {
    private static final Random random = new Random();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    // HashMap для хранения данных
    private static final Map<String, List<String>> DATA_MAP = new HashMap<>();

    static {
        // Инициализация данных
        DATA_MAP.put("lastNames", Arrays.asList(
                "Иванов", "Петров", "Сидоров", "Смирнов", "Кузнецов",
                "Попов", "Лебедев", "Козлов", "Новиков", "Морозов",
                "Федоров", "Макаров", "Николаев", "Орлов", "Захаров"
        ));

        DATA_MAP.put("firstNames", Arrays.asList(
                "Александр", "Дмитрий", "Михаил", "Сергей", "Андрей",
                "Алексей", "Артем", "Илья", "Кирилл", "Никита",
                "Евгений", "Максим", "Владимир", "Константин", "Павел"
        ));

        DATA_MAP.put("middleNames", Arrays.asList(
                "Иванович", "Петрович", "Сергеевич", "Александрович", "Дмитриевич",
                "Андреевич", "Михайлович", "Артемович", "Никитич", "Кириллович",
                "Евгеньевич", "Максимович", "Владимирович", "Константинович", "Павлович"
        ));

        DATA_MAP.put("emailDomains", Arrays.asList(
                "gmail.com", "yahoo.com", "mail.ru", "yandex.ru", "hotmail.com",
                "outlook.com", "protonmail.com", "icloud.com", "rambler.ru"
        ));

        DATA_MAP.put("loginPrefixes", Arrays.asList(
                "super", "mega", "cool", "best", "top",
                "new", "old", "young", "dark", "light",
                "red", "blue", "green", "white", "black"
        ));
    }

    /**
     * Генерация ФИО в формате "Фамилия Имя Отчество"
     */
    public static String generateFullName() {
        return String.format("%s %s %s",
                getRandomElement("lastNames"),
                getRandomElement("firstNames"),
                getRandomElement("middleNames")
        );
    }

    /**
     * Генерация даты рождения (от 18 до 100 лет назад)
     */
    public static String generateBirthDate() {
        LocalDate now = LocalDate.now();
        LocalDate minDate = now.minusYears(100);
        LocalDate maxDate = now.minusYears(18);

        long minDay = minDate.toEpochDay();
        long maxDay = maxDate.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);

        return LocalDate.ofEpochDay(randomDay).format(DATE_FORMATTER);
    }

    /**
     * Генерация номера телефона в формате 8(XXX)XXX-XX-XX
     */
    public static String generatePhoneNumber() {
        String operatorCode = String.format("%03d", 900 + random.nextInt(100));
        return String.format("8(%s)%03d-%02d-%02d",
                operatorCode,
                random.nextInt(1000),
                10 + random.nextInt(90),
                10 + random.nextInt(90)
        );
    }

    /**
     * Генерация email в формате prefix123@domain.com
     */
    public static String generateEmail() {
        return String.format("%s%d@%s",
                getRandomElement("loginPrefixes"),
                1900 + random.nextInt(125),
                getRandomElement("emailDomains")
        );
    }

    /**
     * Генерация логина в формате "ФамилияИ"
     */
    public static String generateLogin() {
        String lastName = getRandomElement("lastNames");
        String firstNameInitial = getRandomElement("firstNames").substring(0, 1);
        return lastName + firstNameInitial;
    }

    /**
     * Получение случайного элемента из указанной категории
     */
    private static String getRandomElement(String category) {
        List<String> items = DATA_MAP.get(category);
        return items.get(random.nextInt(items.size()));
    }

    /**
     * Генерация полного набора персональных данных
     */
    public static Map<String, String> generateFullPersonData() {
        Map<String, String> personData = new LinkedHashMap<>();
        personData.put("ФИО", generateFullName());
        personData.put("Дата рождения", generateBirthDate());
        personData.put("Телефон", generatePhoneNumber());
        personData.put("Email", generateEmail());
        personData.put("Логин", generateLogin());
        return personData;
    }
}

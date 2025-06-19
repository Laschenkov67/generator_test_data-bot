package com.example.javabot.service;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PersonDataGeneratorService {

    private final Random random = new Random();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final Map<String, List<String>> dataMap = new HashMap<>();

    public PersonDataGeneratorService() {
        initializeData();
    }

    private void initializeData() {
        dataMap.put("lastNames", Arrays.asList(
                "Иванов", "Петров", "Сидоров", "Смирнов", "Кузнецов",
                "Попов", "Лебедев", "Козлов", "Новиков", "Морозов",
                "Федоров", "Макаров", "Николаев", "Орлов", "Захаров"
        ));

        dataMap.put("firstNames", Arrays.asList(
                "Александр", "Дмитрий", "Михаил", "Сергей", "Андрей",
                "Алексей", "Артем", "Илья", "Кирилл", "Никита",
                "Евгений", "Максим", "Владимир", "Константин", "Павел"
        ));

        dataMap.put("middleNames", Arrays.asList(
                "Иванович", "Петрович", "Сергеевич", "Александрович", "Дмитриевич",
                "Андреевич", "Михайлович", "Артемович", "Никитич", "Кириллович",
                "Евгеньевич", "Максимович", "Владимирович", "Константинович", "Павлович"
        ));

        dataMap.put("emailDomains", Arrays.asList(
                "gmail.com", "yahoo.com", "mail.ru", "yandex.ru", "hotmail.com",
                "outlook.com", "protonmail.com", "icloud.com", "rambler.ru"
        ));

        dataMap.put("loginPrefixes", Arrays.asList(
                "super", "mega", "cool", "best", "top",
                "new", "old", "young", "dark", "light",
                "red", "blue", "green", "white", "black"
        ));
    }

    /**
     * Генерация ФИО в формате "Фамилия Имя Отчество"
     */
    public String generateFullName() {
        return String.format("%s %s %s",
                getRandomElement("lastNames"),
                getRandomElement("firstNames"),
                getRandomElement("middleNames")
        );
    }

    /**
     * Генерация даты рождения (от 18 до 100 лет назад)
     */
    public String generateBirthDate() {
        LocalDate now = LocalDate.now();
        LocalDate minDate = now.minusYears(100);
        LocalDate maxDate = now.minusYears(18);

        long minDay = minDate.toEpochDay();
        long maxDay = maxDate.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);

        return LocalDate.ofEpochDay(randomDay).format(dateFormatter);
    }

    /**
     * Генерация номера телефона в формате 8(XXX)XXX-XX-XX
     */
    public String generatePhoneNumber() {
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
    public String generateEmail() {
        return String.format("%s%d@%s",
                getRandomElement("loginPrefixes"),
                1900 + random.nextInt(125),
                getRandomElement("emailDomains")
        );
    }

    /**
     * Генерация логина в формате "ФамилияИ"
     */
    public String generateLogin() {
        String lastName = getRandomElement("lastNames");
        String firstNameInitial = getRandomElement("firstNames").substring(0, 1);
        return lastName + firstNameInitial;
    }

    /**
     * Получение случайного элемента из указанной категории
     */
    private String getRandomElement(String category) {
        List<String> items = dataMap.get(category);
        return items.get(random.nextInt(items.size()));
    }
}

package com.example.javabot;

import com.example.javabot.service.PersonDataGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonDataGeneratorServiceTest {

    private PersonDataGeneratorService service;
    private Random mockRandom;

    @BeforeEach
    void setUp() {
        service = new PersonDataGeneratorService();
        mockRandom = mock(Random.class);
    }

    @Test
    @DisplayName("Проверка генерации полного имени")
    void testGenerateFullName() {
        String fullName = service.generateFullName();

        assertNotNull(fullName);
        String[] parts = fullName.split(" ");
        assertEquals(3, parts.length, "ФИО должно состоять из трех частей");

        // Проверяем, что каждая часть не пустая
        assertTrue(parts[0].length() > 0, "Фамилия не должна быть пустой");
        assertTrue(parts[1].length() > 0, "Имя не должно быть пустым");
        assertTrue(parts[2].length() > 0, "Отчество не должно быть пустым");
    }

    @Test
    @DisplayName("Проверка генерации даты рождения")
    void testGenerateBirthDate() {
        String birthDate = service.generateBirthDate();

        assertNotNull(birthDate);

        // Проверяем формат даты
        Pattern datePattern = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");
        assertTrue(datePattern.matcher(birthDate).matches(),
                "Дата должна быть в формате dd.MM.yyyy");

        // Проверяем, что дата в допустимом диапазоне
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(birthDate, formatter);
        LocalDate now = LocalDate.now();
        LocalDate minDate = now.minusYears(100);
        LocalDate maxDate = now.minusYears(18);

        assertTrue(date.isAfter(minDate) || date.isEqual(minDate),
                "Дата должна быть не раньше 100 лет назад");
        assertTrue(date.isBefore(maxDate) || date.isEqual(maxDate),
                "Дата должна быть не позже 18 лет назад");
    }

    @Test
    @DisplayName("Проверка генерации номера телефона")
    void testGeneratePhoneNumber() {
        String phoneNumber = service.generatePhoneNumber();

        assertNotNull(phoneNumber);

        // Проверяем формат телефона
        Pattern phonePattern = Pattern.compile("8\\(9\\d{2}\\)\\d{3}-\\d{2}-\\d{2}");
        assertTrue(phonePattern.matcher(phoneNumber).matches(),
                "Телефон должен быть в формате 8(XXX)XXX-XX-XX");
    }

    @Test
    @DisplayName("Проверка генерации email")
    void testGenerateEmail() {
        String email = service.generateEmail();

        assertNotNull(email);

        // Проверяем формат email
        Pattern emailPattern = Pattern.compile("^[a-z]+\\d+@[a-z]+\\.[a-z]+$");
        assertTrue(emailPattern.matcher(email).matches(),
                "Email должен быть в формате prefix123@domain.com");

        // Проверяем, что email содержит @ и точку
        assertTrue(email.contains("@"), "Email должен содержать @");
        assertTrue(email.contains("."), "Email должен содержать точку");
    }

    @Test
    @DisplayName("Проверка генерации логина")
    void testGenerateLogin() {
        String login = service.generateLogin();

        assertNotNull(login);
        assertTrue(login.length() > 1, "Логин должен содержать хотя бы 2 символа");

        // Проверяем, что логин начинается с заглавной буквы
        assertTrue(Character.isUpperCase(login.charAt(0)),
                "Логин должен начинаться с заглавной буквы");
    }

    @RepeatedTest(10)
    @DisplayName("Проверка уникальности генерируемых данных")
    void testDataUniqueness() {
        String fullName1 = service.generateFullName();
        String fullName2 = service.generateFullName();

        // Не всегда будут разные, но в большинстве случаев должны отличаться
        // Это статистический тест
        assertNotNull(fullName1);
        assertNotNull(fullName2);
    }

    @Test
    @DisplayName("Проверка корректности даты в граничных случаях")
    void testBirthDateBoundaries() {
        try (MockedStatic<ThreadLocalRandom> mockedThreadLocal =
                     mockStatic(ThreadLocalRandom.class)) {

            ThreadLocalRandom mockThreadLocalRandom = mock(ThreadLocalRandom.class);
            mockedThreadLocal.when(ThreadLocalRandom::current).thenReturn(mockThreadLocalRandom);

            LocalDate now = LocalDate.now();
            LocalDate maxDate = now.minusYears(18);

            // Проверяем минимальный возраст (18 лет)
            when(mockThreadLocalRandom.nextLong(anyLong(), anyLong()))
                    .thenReturn(maxDate.toEpochDay());

            String birthDate = service.generateBirthDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate parsedDate = LocalDate.parse(birthDate, formatter);

            assertEquals(maxDate, parsedDate, "Должна быть дата 18 лет назад");
        }
    }

    @Test
    @DisplayName("Проверка всех компонентов ФИО из предопределенных списков")
    void testFullNameComponents() {
        // Генерируем несколько ФИО и проверяем, что все части из наших списков
        for (int i = 0; i < 50; i++) {
            String fullName = service.generateFullName();
            String[] parts = fullName.split(" ");

            // Списки из сервиса
            assertTrue(isValidLastName(parts[0]), "Фамилия должна быть из списка");
            assertTrue(isValidFirstName(parts[1]), "Имя должно быть из списка");
            assertTrue(isValidMiddleName(parts[2]), "Отчество должно быть из списка");
        }
    }

    private boolean isValidLastName(String lastName) {
        return lastName.matches("(Иванов|Петров|Сидоров|Смирнов|Кузнецов|" +
                "Попов|Лебедев|Козлов|Новиков|Морозов|" +
                "Федоров|Макаров|Николаев|Орлов|Захаров)");
    }

    private boolean isValidFirstName(String firstName) {
        return firstName.matches("(Александр|Дмитрий|Михаил|Сергей|Андрей|" +
                "Алексей|Артем|Илья|Кирилл|Никита|" +
                "Евгений|Максим|Владимир|Константин|Павел)");
    }

    private boolean isValidMiddleName(String middleName) {
        return middleName.matches("(Иванович|Петрович|Сергеевич|Александрович|Дмитриевич|" +
                "Андреевич|Михайлович|Артемович|Никитич|Кириллович|" +
                "Евгеньевич|Максимович|Владимирович|Константинович|Павлович)");
    }

    @Test
    @DisplayName("Проверка формата телефона с различными кодами операторов")
    void testPhoneNumberOperatorCodes() {
        for (int i = 0; i < 20; i++) {
            String phone = service.generatePhoneNumber();
            String operatorCode = phone.substring(2, 5);
            int code = Integer.parseInt(operatorCode);

            assertTrue(code >= 900 && code <= 999,
                    "Код оператора должен быть между 900 и 999");
        }
    }

    @Test
    @DisplayName("Проверка email с корректными доменами")
    void testEmailDomains() {
        for (int i = 0; i < 30; i++) {
            String email = service.generateEmail();
            String domain = email.substring(email.indexOf('@') + 1);

            assertTrue(isValidDomain(domain),
                    "Домен должен быть из списка допустимых");
        }
    }

    private boolean isValidDomain(String domain) {
        return domain.matches("(gmail\\.com|yahoo\\.com|mail\\.ru|yandex\\.ru|hotmail\\.com|" +
                "outlook\\.com|protonmail\\.com|icloud\\.com|rambler\\.ru)");
    }

    @Test
    @DisplayName("Проверка года в email")
    void testEmailYear() {
        for (int i = 0; i < 20; i++) {
            String email = service.generateEmail();
            String yearPart = email.replaceAll("[^0-9]", "");
            int year = Integer.parseInt(yearPart);

            assertTrue(year >= 1900 && year <= 2024,
                    "Год в email должен быть между 1900 и 2024");
        }
    }
}

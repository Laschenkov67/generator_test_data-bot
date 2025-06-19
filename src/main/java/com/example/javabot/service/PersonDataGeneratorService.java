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
     * Генерация серии и номера паспорта РФ в формате XXXX XXXXXX
     */
    public String generatePassportNumber() {
        // Серия паспорта (4 цифры): первые 2 цифры - код региона (01-99), следующие 2 - код подразделения
        int regionCode = 1 + random.nextInt(99); // от 01 до 99
        int departmentCode = random.nextInt(100); // от 00 до 99
        String series = String.format("%02d%02d", regionCode, departmentCode);

        // Номер паспорта (6 цифр)
        int passportNumber = 100000 + random.nextInt(900000); // от 100000 до 999999

        return String.format("%s %d", series, passportNumber);
    }

    /**
     * Генерация СНИЛС в формате XXX-XXX-XXX XX
     */
    public String generateSnils() {
        // Генерируем первые 9 цифр
        StringBuilder snilsBuilder = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            snilsBuilder.append(random.nextInt(10));
        }

        String snilsNumber = snilsBuilder.toString();

        // Вычисляем контрольную сумму
        int controlSum = calculateSnilsControlSum(snilsNumber);

        // Формируем итоговый СНИЛС с форматированием
        return String.format("%s-%s-%s %02d",
                snilsNumber.substring(0, 3),
                snilsNumber.substring(3, 6),
                snilsNumber.substring(6, 9),
                controlSum
        );
    }

    /**
     * Вычисление контрольной суммы для СНИЛС
     */
    private int calculateSnilsControlSum(String snilsNumber) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(snilsNumber.charAt(i));
            sum += digit * (9 - i);
        }

        // Применяем правила вычисления контрольной суммы СНИЛС
        if (sum < 100) {
            return sum;
        } else if (sum == 100 || sum == 101) {
            return 0;
        } else {
            int remainder = sum % 101;
            if (remainder < 100) {
                return remainder;
            } else {
                return 0;
            }
        }
    }

    /**
     * Получение случайного элемента из указанной категории
     */
    private String getRandomElement(String category) {
        List<String> items = dataMap.get(category);
        return items.get(random.nextInt(items.size()));
    }

    /**
     * Генерация полного набора персональных данных
     */
    public Map<String, String> generateFullPersonData() {
        Map<String, String> personData = new LinkedHashMap<>();
        personData.put("ФИО", generateFullName());
        personData.put("Дата рождения", generateBirthDate());
        personData.put("Телефон", generatePhoneNumber());
        personData.put("Email", generateEmail());
        personData.put("Логин", generateLogin());
        personData.put("Паспорт", generatePassportNumber());
        personData.put("СНИЛС", generateSnils());
        return personData;
    }

    /**
     * Генерация расширенного набора персональных данных с дополнительными полями
     */
    public Map<String, String> generateExtendedPersonData() {
        Map<String, String> personData = generateFullPersonData();
        // Можно добавить дополнительные поля при необходимости
        return personData;
    }

    /**
     * Генерация только документов (паспорт и СНИЛС)
     */
    public Map<String, String> generateDocuments() {
        Map<String, String> documents = new LinkedHashMap<>();
        documents.put("Паспорт", generatePassportNumber());
        documents.put("СНИЛС", generateSnils());
        return documents;
    }

    /**
     * Генерация списка персональных данных для заданного количества людей
     */
    public List<Map<String, String>> generateMultiplePersonsData(int count) {
        List<Map<String, String>> personsList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            personsList.add(generateFullPersonData());
        }
        return personsList;
    }

    /**
     * Валидация СНИЛС (проверка контрольной суммы)
     */
    public boolean validateSnils(String snils) {
        // Убираем форматирование
        String cleanSnils = snils.replaceAll("[^0-9]", "");

        if (cleanSnils.length() != 11) {
            return false;
        }

        String number = cleanSnils.substring(0, 9);
        int providedControlSum = Integer.parseInt(cleanSnils.substring(9));
        int calculatedControlSum = calculateSnilsControlSum(number);

        return providedControlSum == calculatedControlSum;
    }

    /**
     * Получение всех доступных категорий данных
     */
    public Set<String> getAvailableCategories() {
        return dataMap.keySet();
    }

    /**
     * Добавление новых данных в определенную категорию
     */
    public void addDataToCategory(String category, List<String> newData) {
        dataMap.computeIfAbsent(category, k -> new ArrayList<>()).addAll(newData);
    }

    /**
     * Получение всех данных определенной категории
     */
    public List<String> getDataByCategory(String category) {
        return new ArrayList<>(dataMap.getOrDefault(category, Collections.emptyList()));
    }
}

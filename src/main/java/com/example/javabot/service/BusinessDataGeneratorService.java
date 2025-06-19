package com.example.javabot.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class BusinessDataGeneratorService {

    private final Random random = new Random();

    /**
     * Генерация ИНН юридического лица (10 цифр)
     */
    public String generateJuridicalInn() {
        List<Integer> digits = generateRandomDigits(9);
        int checksum = calculateJuridicalInnChecksum(digits);
        return digitsToString(digits) + checksum;
    }

    /**
     * Генерация ИНН физического лица (12 цифр)
     */
    public String generateIndividualInn() {
        List<Integer> digits = generateRandomDigits(10);
        int checksum1 = calculateIndividualInnChecksum(digits, 11);
        digits.add(checksum1);
        int checksum2 = calculateIndividualInnChecksum(digits, 12);
        return digitsToString(digits) + checksum2;
    }

    /**
     * Генерация ОГРН (13 цифр)
     */
    public String generateOgrn() {
        List<Integer> digits = generateRandomDigits(12);
        int checksum = (int) (digitsToLong(digits) % 11 % 10);
        return digitsToString(digits) + checksum;
    }

    /**
     * Генерация ОГРН ИП (15 цифр)
     */
    public String generateOgrnIp() {
        List<Integer> digits = generateRandomDigits(14);
        int checksum = (int) (digitsToLong(digits) % 13 % 10);
        return digitsToString(digits) + checksum;
    }

    /**
     * Генерация серии и номера паспорта РФ в формате "XXXX XXXXXX"
     */
    public String generatePassportNumber() {
        // Серия паспорта (4 цифры)
        String series = String.format("%04d", 1000 + random.nextInt(9000));
        // Номер паспорта (6 цифр)
        String number = String.format("%06d", 100000 + random.nextInt(900000));
        return series + " " + number;
    }

    /**
     * Генерация СНИЛС в формате "XXX-XXX-XXX XX"
     */
    public String generateSnilsGosKey() {
        // Генерируем 9 цифр для основной части СНИЛС
        StringBuilder snilsNumber = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            snilsNumber.append(random.nextInt(10));
        }

        // Рассчитываем контрольную сумму
        int checksum = calculateSnilsChecksum(snilsNumber.toString());

        // Форматируем СНИЛС
        return String.format("%s-%s-%s %02d",
                snilsNumber.substring(0, 3),
                snilsNumber.substring(3, 6),
                snilsNumber.substring(6, 9),
                checksum
        );
    }

    /**
     * Генерация СНИЛС (XXX-XXX-XXX YY)
     */
    public String generateSnils() {
        List<Integer> digits = generateRandomDigits(9);
        int checksum = random.nextInt(90) + 10; // от 10 до 99
        return String.format("%s-%s-%s %02d",
                digitsToString(digits.subList(0, 3)),
                digitsToString(digits.subList(3, 6)),
                digitsToString(digits.subList(6, 9)),
                checksum
        );
    }

    /**
     * Генерация ЕНП ОМС (16 цифр)
     */
    public String generateEnpOms() {
        List<Integer> digits = generateRandomDigits(15);
        int checksum = calculateEnpOmsChecksum(digits);
        return digitsToString(digits) + checksum;
    }

    /**
     * Генерация ОКПО юридического лица (8 цифр)
     */
    public String generateOkpo() {
        List<Integer> digits = generateRandomDigits(7);
        int checksum = calculateOkpoChecksum(digits, 8);
        return digitsToString(digits) + checksum;
    }

    /**
     * Генерация ОКПО ИП (10 цифр)
     */
    public String generateOkpoIp() {
        List<Integer> digits = generateRandomDigits(9);
        int checksum = calculateOkpoChecksum(digits, 10);
        return digitsToString(digits) + checksum;
    }

    /**
     * Генерация списка случайных цифр указанной длины
     */
    public List<Integer> generateRandomDigits(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> random.nextInt(10))
                .collect(Collectors.toList());
    }

    /**
     * Преобразование списка цифр в строку
     */
    public String digitsToString(List<Integer> digits) {
        return digits.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    /**
     * Преобразование списка цифр в long число
     */
    public long digitsToLong(List<Integer> digits) {
        return Long.parseLong(digitsToString(digits));
    }

    /**
     * Расчет контрольной цифры для ИНН юридического лица
     */
    public int calculateJuridicalInnChecksum(List<Integer> digits) {
        int[] weights = {2, 4, 10, 3, 5, 9, 4, 6, 8};
        int sum = IntStream.range(0, 9)
                .map(i -> digits.get(i) * weights[i])
                .sum();
        return (sum % 11) % 10;
    }

    /**
     * Расчет контрольной цифры для ИНН физического лица
     */
    public int calculateIndividualInnChecksum(List<Integer> digits, int length) {
        int[] weights = length == 11 ?
                new int[]{7, 2, 4, 10, 3, 5, 9, 4, 6, 8} :
                new int[]{3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8};

        int sum = IntStream.range(0, weights.length)
                .map(i -> digits.get(i) * weights[i])
                .sum();
        return (sum % 11) % 10;
    }

    /**
     * Расчет контрольной цифры для ЕНП ОМС (алгоритм Луна)
     */
    public int calculateEnpOmsChecksum(List<Integer> digits) {
        int sum = 0;
        for (int i = digits.size() - 1; i >= 0; i--) {
            int digit = digits.get(i);
            if ((digits.size() - i) % 2 == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }
        return (10 - (sum % 10)) % 10;
    }

    /**
     * Расчет контрольной цифры для ОКПО
     */
    public int calculateOkpoChecksum(List<Integer> digits, int length) {
        int sum = 0;

        if (length == 8) {
            // Для 8-значного ОКПО используем веса 1, 2, 3, 4, 5, 6, 7
            for (int i = 0; i < 7; i++) {
                sum += digits.get(i) * (i + 1);
            }
        } else {
            // Для 10-значного ОКПО используем веса 1, 2, 3, 4, 5, 6, 7, 8, 9
            for (int i = 0; i < 9; i++) {
                sum += digits.get(i) * (i + 1);
            }
        }

        int remainder = sum % 11;

        // Если остаток равен 10, применяем альтернативный алгоритм
        if (remainder == 10) {
            sum = 0;
            if (length == 8) {
                // Веса 3, 4, 5, 6, 7, 8, 9
                for (int i = 0; i < 7; i++) {
                    sum += digits.get(i) * (i + 3);
                }
            } else {
                // Веса 3, 4, 5, 6, 7, 8, 9, 10, 11
                for (int i = 0; i < 9; i++) {
                    sum += digits.get(i) * (i + 3);
                }
            }
            remainder = sum % 11;
            if (remainder == 10) {
                remainder = 0;
            }
        }

        return remainder;
    }

    /**
     * Расчет контрольной суммы СНИЛС
     */
    public int calculateSnilsChecksum(String snilsNumber) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(snilsNumber.charAt(i)) * (9 - i);
        }

        if (sum < 100) {
            return sum;
        } else if (sum == 100 || sum == 101) {
            return 0;
        } else {
            return sum % 101;
        }
    }
}

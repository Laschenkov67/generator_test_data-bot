package com.example.javabot;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
public class BusinessDataGenerator {
    private static final Random random = new Random();

    /**
     * Генерация ИНН юридического лица (10 цифр)
     */
    public static String generateJuridicalInn() {
        List<Integer> digits = generateRandomDigits(9);
        int checksum = calculateJuridicalInnChecksum(digits);
        return digitsToString(digits) + checksum;
    }

    /**
     * Генерация ИНН физического лица (12 цифр)
     */
    public static String generateIndividualInn() {
        List<Integer> digits = generateRandomDigits(10);
        int checksum1 = calculateIndividualInnChecksum(digits, 11);
        digits.add(checksum1);
        int checksum2 = calculateIndividualInnChecksum(digits, 12);
        return digitsToString(digits) + checksum2;
    }

    /**
     * Генерация ОГРН (13 цифр)
     */
    public static String generateOgrn() {
        List<Integer> digits = generateRandomDigits(12);
        int checksum = (int) (digitsToLong(digits) % 11 % 10);
        return digitsToString(digits) + checksum;
    }

    /**
     * Генерация ОГРН ИП (15 цифр)
     */
    public static String generateOgrnIp() {
        List<Integer> digits = generateRandomDigits(14);
        int checksum = (int) (digitsToLong(digits) % 13 % 10);
        return digitsToString(digits) + checksum;
    }

    /**
     * Генерация СНИЛС (XXX-XXX-XXX YY)
     */
    public static String generateSnils() {
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
     * Генерация списка случайных цифр указанной длины
     */
    private static List<Integer> generateRandomDigits(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> random.nextInt(10))
                .collect(Collectors.toList());
    }

    /**
     * Преобразование списка цифр в строку
     */
    private static String digitsToString(List<Integer> digits) {
        return digits.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    /**
     * Преобразование списка цифр в long число
     */
    private static long digitsToLong(List<Integer> digits) {
        return Long.parseLong(digitsToString(digits));
    }

    /**
     * Расчет контрольной цифры для ИНН юридического лица
     */
    private static int calculateJuridicalInnChecksum(List<Integer> digits) {
        int[] weights = {2, 4, 10, 3, 5, 9, 4, 6, 8};
        int sum = IntStream.range(0, 9)
                .map(i -> digits.get(i) * weights[i])
                .sum();
        return (sum % 11) % 10;
    }

    /**
     * Расчет контрольной цифры для ИНН физического лица
     */
    private static int calculateIndividualInnChecksum(List<Integer> digits, int length) {
        int[] weights = length == 11 ?
                new int[]{7, 2, 4, 10, 3, 5, 9, 4, 6, 8} :
                new int[]{3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8};

        int sum = IntStream.range(0, weights.length)
                .map(i -> digits.get(i) * weights[i])
                .sum();
        return (sum % 11) % 10;
    }
}

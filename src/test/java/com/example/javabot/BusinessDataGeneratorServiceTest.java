package com.example.javabot;

import com.example.javabot.service.BusinessDataGeneratorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BusinessDataGeneratorServiceTest {

    @Mock
    private Random random;

    @InjectMocks
    private BusinessDataGeneratorService service;

    @Test
    void generateJuridicalInn_shouldReturnValidInn() {
        when(random.nextInt(10)).thenReturn(1, 2, 3, 4, 5, 6, 7, 8, 9);

        String inn = service.generateJuridicalInn();

        assertEquals(10, inn.length());
        assertTrue(inn.matches("\\d{10}"));
        assertEquals("1234567896", inn);
    }

    @Test
    void generateIndividualInn_shouldReturnValidInn() {
        when(random.nextInt(10)).thenReturn(1, 2, 3, 4, 5, 6, 7, 8, 9, 0);

        String inn = service.generateIndividualInn();

        assertEquals(12, inn.length());
        assertTrue(inn.matches("\\d{12}"));
        assertEquals("123456789087", inn);
    }

    @Test
    void generateOgrn_shouldReturnValidOgrn() {
        when(random.nextInt(10)).thenReturn(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        String ogrn = service.generateOgrn();

        assertEquals(13, ogrn.length());
        assertTrue(ogrn.matches("\\d{13}"));
        assertEquals("1000000000005", ogrn);
    }

    @Test
    void generateOgrnIp_shouldReturnValidOgrnIp() {
        when(random.nextInt(10)).thenReturn(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        String ogrnIp = service.generateOgrnIp();

        assertEquals(15, ogrnIp.length());
        assertTrue(ogrnIp.matches("\\d{15}"));
        assertEquals("100000000000009", ogrnIp);
    }

    @Test
    void generatePassportNumber_shouldReturnValidFormat() {
        when(random.nextInt(9000)).thenReturn(1234);
        when(random.nextInt(900000)).thenReturn(56789);

        String passport = service.generatePassportNumber();

        assertTrue(passport.matches("\\d{4} \\d{6}"));
        assertEquals("2234 156789", passport);
    }

    @Test
    void generateSnilsGosKey_shouldReturnValidSnils() {
        when(random.nextInt(10)).thenReturn(1, 2, 3, 4, 5, 6, 7, 8, 9);

        String snils = service.generateSnilsGosKey();

        assertTrue(snils.matches("\\d{3}-\\d{3}-\\d{3} \\d{2}"));
        assertEquals("123-456-789 00", snils);
    }

    @Test
    void generateSnils_shouldReturnValidFormat() {
        when(random.nextInt(10)).thenReturn(1, 2, 3, 4, 5, 6, 7, 8, 9);
        when(random.nextInt(90)).thenReturn(89);

        String snils = service.generateSnils();

        assertTrue(snils.matches("\\d{3}-\\d{3}-\\d{3} \\d{2}"));
        assertEquals("123-456-789 99", snils);
    }

    @Test
    void generateEnpOms_shouldReturnValidEnp() {
        when(random.nextInt(10)).thenReturn(7, 9, 9, 2, 7, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        String enp = service.generateEnpOms();

        assertEquals(16, enp.length());
        assertTrue(enp.matches("\\d{16}"));
        assertEquals("7992730000000008", enp);
    }

    @Test
    void generateOkpo_shouldReturnValidOkpo() {
        when(random.nextInt(10)).thenReturn(1, 2, 3, 4, 5, 6, 7);

        String okpo = service.generateOkpo();

        assertEquals(8, okpo.length());
        assertTrue(okpo.matches("\\d{8}"));
        assertEquals("12345671", okpo);
    }

    @Test
    void generateOkpoIp_shouldReturnValidOkpoIp() {
        when(random.nextInt(10)).thenReturn(1, 2, 3, 4, 5, 6, 7, 8, 9);

        String okpoIp = service.generateOkpoIp();

        assertEquals(10, okpoIp.length());
        assertTrue(okpoIp.matches("\\d{10}"));
        assertEquals("1234567895", okpoIp);
    }

    @Test
    void generateRandomDigits_shouldReturnListOfSpecifiedLength() {
        when(random.nextInt(10)).thenReturn(1, 2, 3);

        List<Integer> digits = service.generateRandomDigits(3);

        assertEquals(3, digits.size());
        assertEquals(List.of(1, 2, 3), digits);
    }

    @Test
    void digitsToString_shouldConvertDigitsToString() {
        String result = service.digitsToString(List.of(1, 2, 3));
        assertEquals("123", result);
    }

    @Test
    void digitsToLong_shouldConvertDigitsToLong() {
        long result = service.digitsToLong(List.of(1, 2, 3));
        assertEquals(123L, result);
    }

    @Test
    void calculateJuridicalInnChecksum_shouldReturnCorrectChecksum() {
        int checksum = service.calculateJuridicalInnChecksum(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        assertEquals(6, checksum);
    }

    @Test
    void calculateIndividualInnChecksum_shouldReturnCorrectChecksums() {
        int checksum1 = service.calculateIndividualInnChecksum(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0), 11);
        assertEquals(8, checksum1);

        int checksum2 = service.calculateIndividualInnChecksum(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 8), 12);
        assertEquals(7, checksum2);
    }

    @Test
    void calculateEnpOmsChecksum_shouldReturnCorrectChecksum() {
        int checksum = service.calculateEnpOmsChecksum(List.of(7, 9, 9, 2, 7, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        assertEquals(8, checksum);
    }

    @Test
    void calculateOkpoChecksum_shouldReturnCorrectChecksumFor8Digits() {
        int checksum = service.calculateOkpoChecksum(List.of(1, 2, 3, 4, 5, 6, 7), 8);
        assertEquals(1, checksum);
    }

    @Test
    void calculateOkpoChecksum_shouldReturnCorrectChecksumFor10Digits() {
        int checksum = service.calculateOkpoChecksum(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9), 10);
        assertEquals(5, checksum);
    }

    @Test
    void calculateSnilsChecksum_shouldReturnCorrectChecksum() {
        int checksum = service.calculateSnilsChecksum("123456789");
        assertEquals(0, checksum);
    }
}

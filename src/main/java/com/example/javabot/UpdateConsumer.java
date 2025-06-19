package com.example.javabot;

import com.example.javabot.service.BusinessDataGeneratorService;
import com.example.javabot.service.GuidUuidGeneratorService;
import com.example.javabot.service.PersonDataGeneratorService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class UpdateConsumer implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;
    private final BusinessDataGeneratorService businessDataGeneratorService;
    private final PersonDataGeneratorService personDataGeneratorService;
    private final GuidUuidGeneratorService guidUuidGeneratorService;

    public UpdateConsumer(BusinessDataGeneratorService businessDataGeneratorService,
                          PersonDataGeneratorService personDataGeneratorService,
                          GuidUuidGeneratorService guidUuidGeneratorService ) {
        this.telegramClient = new OkHttpTelegramClient(
                "1069414284:AAGXLYjclI6P1Wj7t-j9yDGoF-7jDRpUbEk"
        );
        this.businessDataGeneratorService = businessDataGeneratorService;
        this.personDataGeneratorService = personDataGeneratorService;
        this.guidUuidGeneratorService = guidUuidGeneratorService;
    }

    @SneakyThrows
    @Override
    public void consume(Update update) {
        if (update.hasMessage()) {
            var message = update.getMessage();
            var chatId = message.getChatId();

            switch (message.getText()) {
                case "/start"        -> sendReplyKeyboard(chatId);
                case "ИНН"           -> GenerateJuridicalInn(chatId);
                case "ИНН ФЛ"        -> GenerateInnFl(chatId);
                case "ОГРН"          -> GenerateOgrn(chatId);
                case "ОГРН ИП"       -> GenerateOgrnIp(chatId);
                case "ОКПО"          -> GenerateOkpo(chatId);
                case "ОКПО ИП"       -> GenerateOkpoIp(chatId);
                case "ЕНП ОМС"       -> GenerateEnpOms(chatId);
                case "Серия и номер паспорта РФ" -> GeneratePassportNumber(chatId);
                case "СНИЛС"         -> GenerateSnils(chatId);
                case "СНИЛС ГОСКЛЮЧ" -> GenerateSnilsGosKey(chatId);
                case "ФИО"           -> GenerateFullName(chatId);
                case "Дата рождения" -> GenerateBirthDate(chatId);
                case "Логин"         -> GenerateLogin(chatId);
                case "E-mail"        -> GenerateEmail(chatId);
                case "Телефон"       -> GeneratePhoneNumber(chatId);
                case "GUID"          -> GenerateGUID(chatId);
                case "GUID LOWER"    -> GenerateLowerGUID(chatId);
                case "UUID"          -> GenerateUUID(chatId);
                default              -> sendMessage(chatId, "Я вас не понимаю");
            }
        }
    }

    @SneakyThrows
    private void sendReplyKeyboard(Long chatId) {
        // Текст сообщения
        SendMessage message = SendMessage.builder()
                .chatId(chatId.toString())
                .text("Выберите значение из списка:")
                .build();

        // Все кнопки, которые нужно отобразить
        List<String> buttons = List.of(
                "ИНН", "ИНН ФЛ", "ОГРН",
                "ОГРН ИП", "ОКПО", "ОКПО ИП","СНИЛС",
                "СНИЛС ГОСКЛЮЧ", "ЕНП ОМС", "Серия и номер паспорта РФ",
                "ФИО", "Дата рождения", "Логин", "E-mail",
                "Телефон", "GUID", "UUID", "GUID LOWER"
        );

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        int buttonsPerRow = 3;

        for (int i = 0; i < buttons.size(); i += buttonsPerRow) {
            KeyboardRow row = new KeyboardRow();
            buttons.stream()
                    .skip(i)
                    .limit(buttonsPerRow)
                    .forEach(row::add);
            keyboardRows.add(row);
        }

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(keyboardRows);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(false);

        message.setReplyMarkup(markup);
        telegramClient.execute(message);
    }

    @SneakyThrows
    private void sendMessage(
            Long chatId,
            String messageText
    ) {
        SendMessage message = SendMessage.builder()
                .text(messageText)
                .chatId(chatId)
                .build();

        telegramClient.execute(message);
    }

    private void GenerateJuridicalInn(Long chatId) {
        String randomInn = businessDataGeneratorService.generateJuridicalInn();
        sendMessage(chatId, randomInn);
    }

    private void GenerateInnFl(Long chatId) {
        String randomInnFl = businessDataGeneratorService.generateIndividualInn();
        sendMessage(chatId, randomInnFl);
    }

    private void GenerateOgrn(Long chatId) {
        String randomOgrn = businessDataGeneratorService.generateOgrn();
        sendMessage(chatId, randomOgrn);
    }

    private void GenerateOgrnIp(Long chatId) {
        String randomOgrnIp = businessDataGeneratorService.generateOgrnIp();
        sendMessage(chatId, randomOgrnIp);
    }

    private void GenerateOkpo(Long chatId) {
        String randomOkpo = businessDataGeneratorService.generateOkpo();
        sendMessage(chatId, randomOkpo);
    }

    /**
     * @param chatId
     */
    private void GenerateOkpoIp(Long chatId) {
        String randomOkpoIp = businessDataGeneratorService.generateOkpoIp();
        sendMessage(chatId, randomOkpoIp);
    }

    private void GenerateEnpOms(Long chatId) {
        String randomEnpOms = businessDataGeneratorService.generateEnpOms();
        sendMessage(chatId, randomEnpOms);
    }

    private void GeneratePassportNumber(Long chatId) {
        String randomPassportNumber = businessDataGeneratorService.generatePassportNumber();
        sendMessage(chatId, randomPassportNumber);
    }

    private void GenerateSnilsGosKey(Long chatId) {
        String randomGosKey = businessDataGeneratorService.generateSnilsGosKey();
        sendMessage(chatId, randomGosKey);
    }

    private void GenerateSnils(Long chatId) {
        String randomSnils = businessDataGeneratorService.generateSnils();
        sendMessage(chatId, randomSnils);
    }

    private void GenerateFullName(Long chatId) {
        String fullName = personDataGeneratorService.generateFullName();
        sendMessage(chatId, fullName);
    }

    private void GenerateBirthDate(Long chatId) {
        String birthDate = personDataGeneratorService.generateBirthDate();
        sendMessage(chatId, birthDate);
    }

    private void GeneratePhoneNumber(Long chatId) {
        String phoneNumber = personDataGeneratorService.generatePhoneNumber();
        sendMessage(chatId, phoneNumber);
    }

    private void GenerateEmail(Long chatId) {
        String email = personDataGeneratorService.generateEmail();
        sendMessage(chatId, email);
    }

    private void GenerateLogin(Long chatId) {
        String login = personDataGeneratorService.generateLogin();
        sendMessage(chatId, login);
    }

    private void GenerateGUID(Long chatId) {
        String guid = guidUuidGeneratorService.generateGuid();
        sendMessage(chatId, guid);
    }

    private void GenerateLowerGUID(Long chatId) {
        String guid = guidUuidGeneratorService.generateGuidLower();
        sendMessage(chatId, guid);
    }

    private void GenerateUUID(Long chatId) {
        String guid = guidUuidGeneratorService.generateUuid();
        sendMessage(chatId, guid);
    }
}

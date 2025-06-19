package com.example.javabot;

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

    public UpdateConsumer() {
        this.telegramClient = new OkHttpTelegramClient(
                "1069414284:AAGXLYjclI6P1Wj7t-j9yDGoF-7jDRpUbEk"
        );
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
                case "СНИЛС"         -> GenerateSnils(chatId);
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
                "ОГРН ИП", "СНИЛС", "ФИО",
                "Дата рождения", "Логин", "E-mail",
                "Телефон", "GUID", "UUID", "ЕНП ОМС",
                "ОКПО", "ОКПО ИП", "GUID LOWER", "Серия и номер паспорта РФ",
                "СНИЛС ГОСКЛЮЧ"
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
        String randomInn = BusinessDataGenerator.generateJuridicalInn();
        sendMessage(chatId, randomInn);
    }

    private void GenerateInnFl(Long chatId) {
        String randomInnFl = BusinessDataGenerator.generateIndividualInn();
        sendMessage(chatId, randomInnFl);
    }

    private void GenerateOgrn(Long chatId) {
        String randomOgrn = BusinessDataGenerator.generateOgrn();
        sendMessage(chatId, randomOgrn);
    }

    private void GenerateOgrnIp(Long chatId) {
        String randomOgrnIp = BusinessDataGenerator.generateOgrnIp();
        sendMessage(chatId, randomOgrnIp);
    }

    private void GenerateSnils(Long chatId) {
        String randomSnils = BusinessDataGenerator.generateSnils();
        sendMessage(chatId, randomSnils);
    }

    private void GenerateFullName(Long chatId) {
        String fullName = PersonDataGenerator.generateFullName();
        sendMessage(chatId, fullName);
    }

    private void GenerateBirthDate(Long chatId) {
        String birthDate = PersonDataGenerator.generateBirthDate();
        sendMessage(chatId, birthDate);
    }

    private void GeneratePhoneNumber(Long chatId) {
        String phoneNumber = PersonDataGenerator.generatePhoneNumber();
        sendMessage(chatId, phoneNumber);
    }

    private void GenerateEmail(Long chatId) {
        String email = PersonDataGenerator.generateEmail();
        sendMessage(chatId, email);
    }

    private void GenerateLogin(Long chatId) {
        String login = PersonDataGenerator.generateLogin();
        sendMessage(chatId, login);
    }

    private void GenerateGUID(Long chatId) {
        String guid = GuidUuidGenerator.generateGuid();
        sendMessage(chatId, guid);
    }

    private void GenerateLowerGUID(Long chatId) {
        String guid = GuidUuidGenerator.generateGuidLower();
        sendMessage(chatId, guid);
    }

    private void GenerateUUID(Long chatId) {
        String guid = GuidUuidGenerator.generateUuid();
        sendMessage(chatId, guid);
    }
}

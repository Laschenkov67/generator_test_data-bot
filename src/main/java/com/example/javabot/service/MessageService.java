package com.example.javabot.service;

import com.example.javabot.config.TelegramBotConfig;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageService {

    private final OkHttpTelegramClient telegramClient;
    private final KeyboardService keyboardService;

    public MessageService(TelegramBotConfig config, KeyboardService keyboardService) {
        this.telegramClient = new OkHttpTelegramClient(config.getToken());
        this.keyboardService = keyboardService;
    }

    public void sendMessage(Long chatId, String text) {
        try {
            SendMessage message = SendMessage.builder()
                    .chatId(chatId.toString())
                    .text(text)
                    .build();
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            log.error("Failed to send message to chat {}: {}", chatId, e.getMessage());
        }
    }

    public void sendKeyboard(Long chatId) {
        try {
            SendMessage message = SendMessage.builder()
                    .chatId(chatId.toString())
                    .text("Выберите значение из списка:")
                    .replyMarkup(keyboardService.createMainKeyboard())
                    .build();
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            log.error("Failed to send keyboard to chat {}: {}", chatId, e.getMessage());
        }
    }
}

package com.example.javabot;

import com.example.javabot.config.TelegramBotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
@Component
public class TestDataTelegramBot implements SpringLongPollingBot {
    private final UpdateConsumer updateConsumer;
    private final TelegramBotConfig telegramBotConfig;

    public TestDataTelegramBot(UpdateConsumer updateConsumer, TelegramBotConfig telegramBotConfig) {
        this.updateConsumer = updateConsumer;
        this.telegramBotConfig = telegramBotConfig;
    }

    @Override
    public String getBotToken() {
        return telegramBotConfig.getToken();
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return updateConsumer;
    }
}

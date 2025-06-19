package com.example.javabot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
@Component
public class TestDataTelegramBot implements SpringLongPollingBot {
    private final UpdateConsumer updateConsumer;

    public TestDataTelegramBot(UpdateConsumer updateConsumer) {
        this.updateConsumer = updateConsumer;
    }

    @Override
    public String getBotToken() {
        return "1069414284:AAGXLYjclI6P1Wj7t-j9yDGoF-7jDRpUbEk";
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return updateConsumer;
    }
}

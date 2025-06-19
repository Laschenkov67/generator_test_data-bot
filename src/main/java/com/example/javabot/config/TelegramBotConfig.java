package com.example.javabot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
public class TelegramBotConfig {
    @Value("${telegram.bot.token}")
    private String token;
}

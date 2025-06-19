package com.example.javabot;

import com.example.javabot.command.BotCommand;
import com.example.javabot.service.CommandHandlerService;
import com.example.javabot.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateConsumer implements LongPollingSingleThreadUpdateConsumer {

    private static final String START_COMMAND = "/start";
    private static final String UNKNOWN_COMMAND_MESSAGE = "Я вас не понимаю";

    private final CommandHandlerService commandHandlerService;
    private final MessageService messageService;

    @Override
    public void consume(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        var message = update.getMessage();
        var chatId = message.getChatId();
        var text = message.getText();

        if (START_COMMAND.equals(text)) {
            messageService.sendKeyboard(chatId);
            return;
        }

        BotCommand command = BotCommand.fromDisplayName(text);
        if (command != null) {
            String result = commandHandlerService.handle(command);
            messageService.sendMessage(chatId, result);
        } else {
            messageService.sendMessage(chatId, UNKNOWN_COMMAND_MESSAGE);
        }
    }
}

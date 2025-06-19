package com.example.javabot.service;
import com.example.javabot.command.BotCommand;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

@Service
public class KeyboardService {

    private static final int BUTTONS_PER_ROW = 3;

    public ReplyKeyboardMarkup createMainKeyboard() {
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        List<String> buttonNames = Arrays.stream(BotCommand.values())
                .map(BotCommand::getDisplayName)
                .toList();

        for (int i = 0; i < buttonNames.size(); i += BUTTONS_PER_ROW) {
            KeyboardRow row = new KeyboardRow();
            buttonNames.stream()
                    .skip(i)
                    .limit(BUTTONS_PER_ROW)
                    .forEach(row::add);
            keyboardRows.add(row);
        }

        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboardRows)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }
}

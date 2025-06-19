package com.example.javabot;
import com.example.javabot.config.TelegramBotConfig;
import com.example.javabot.service.KeyboardService;
import com.example.javabot.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private TelegramBotConfig config;

    @Mock
    private KeyboardService keyboardService;

    @Mock
    private OkHttpTelegramClient telegramClient;

    private MessageService messageService;

    @BeforeEach
    void setUp() {
        when(config.getToken()).thenReturn("test-token");

        // Используем MockedConstruction для мокирования конструктора OkHttpTelegramClient
        try (MockedConstruction<OkHttpTelegramClient> mockedClient =
                     mockConstruction(OkHttpTelegramClient.class, (mock, context) -> {
                         // Заменяем созданный объект нашим моком
                         telegramClient = mock;
                     })) {
            messageService = new MessageService(config, keyboardService);
        }
    }

    @Test
    void sendMessage_Success() throws TelegramApiException {
        // Given
        Long chatId = 12345L;
        String text = "Test message";

        // When
        messageService.sendMessage(chatId, text);

        // Then
        ArgumentCaptor<SendMessage> messageCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramClient).execute(messageCaptor.capture());

        SendMessage capturedMessage = messageCaptor.getValue();
        assertEquals(chatId.toString(), capturedMessage.getChatId());
        assertEquals(text, capturedMessage.getText());
        assertNull(capturedMessage.getReplyMarkup());
    }

    @Test
    void sendMessage_ExceptionHandling() throws TelegramApiException {
        // Given
        Long chatId = 12345L;
        String text = "Test message";
        TelegramApiException exception = new TelegramApiException("API Error");

        when(telegramClient.execute(any(SendMessage.class))).thenThrow(exception);

        // When & Then
        assertDoesNotThrow(() -> messageService.sendMessage(chatId, text));
        verify(telegramClient).execute(any(SendMessage.class));
    }

    @Test
    void sendKeyboard_Success() throws TelegramApiException {
        // Given
        Long chatId = 54321L;
        ReplyKeyboardMarkup mockKeyboard = mock(ReplyKeyboardMarkup.class);
        when(keyboardService.createMainKeyboard()).thenReturn(mockKeyboard);

        // When
        messageService.sendKeyboard(chatId);

        // Then
        ArgumentCaptor<SendMessage> messageCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramClient).execute(messageCaptor.capture());

        SendMessage capturedMessage = messageCaptor.getValue();
        assertEquals(chatId.toString(), capturedMessage.getChatId());
        assertEquals("Выберите значение из списка:", capturedMessage.getText());
        assertEquals(mockKeyboard, capturedMessage.getReplyMarkup());
    }

    @Test
    void sendKeyboard_ExceptionHandling() throws TelegramApiException {
        // Given
        Long chatId = 54321L;
        ReplyKeyboardMarkup mockKeyboard = mock(ReplyKeyboardMarkup.class);
        when(keyboardService.createMainKeyboard()).thenReturn(mockKeyboard);

        TelegramApiException exception = new TelegramApiException("Keyboard API Error");
        when(telegramClient.execute(any(SendMessage.class))).thenThrow(exception);

        // When & Then
        assertDoesNotThrow(() -> messageService.sendKeyboard(chatId));
        verify(telegramClient).execute(any(SendMessage.class));
        verify(keyboardService).createMainKeyboard();
    }
}

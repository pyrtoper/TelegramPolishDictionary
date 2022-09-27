package com.pyrtoper.dictionary;

import static org.junit.jupiter.api.Assertions.*;
import com.pyrtoper.dictionary.bot.DictionaryBot;
import com.pyrtoper.dictionary.entity.Word;
import com.pyrtoper.dictionary.service.WordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.Collection;

@SpringBootTest(classes = DictionaryApplication.class)
public class UpdateTests {

    private Message message;
    private Update update;

    private CallbackQuery callbackQuery;
    private static WordService wordService;
    @Autowired
    private DictionaryBot dictionaryBot;

    @Autowired
    public void setWordService(WordService wordService) {
        UpdateTests.wordService = wordService;
    }

    @BeforeEach
    void setup() {
        message = Mockito.mock(Message.class);
        update = Mockito.mock(Update.class);
        callbackQuery = Mockito.mock(CallbackQuery.class);
        Mockito.when(message.getChatId()).thenReturn(1L);
        Mockito.when(callbackQuery.getMessage()).thenReturn(message);
        Mockito.when(update.getMessage()).thenReturn(message);
        Mockito.when(update.getCallbackQuery()).thenReturn(callbackQuery);
    }

    @ParameterizedTest
    @MethodSource("testPolishWords")
    public void checkSendingMessage(String wordName) {
        Mockito.when(message.getText()).thenReturn(wordName);
        Mockito.when(update.hasMessage()).thenReturn(true);
        Word word = wordService.getWordByName(wordName);
        SendMessage sendMessage = (SendMessage) dictionaryBot.onWebhookUpdateReceived(update);
        assertEquals(sendMessage.getText(), word.toString());
    }

    @ParameterizedTest
    @MethodSource("testPolishWords")
    public void checkCallbackQueryUpdate(String wordName) {
        Mockito.when(update.hasCallbackQuery()).thenReturn(true);
        Mockito.when(callbackQuery.getData()).thenReturn(wordName);
        Word word = wordService.getWordByName(wordName);
        SendMessage sendMessage = (SendMessage) dictionaryBot.onWebhookUpdateReceived(update);
        assertEquals(word.toString(), sendMessage.getText());
    }

    @ParameterizedTest
    @MethodSource("testPolishWords")
    public void checkWrongWorkState(String wordName) {
        DictionaryBot.setPolishToRussianWorkState(false);
        Mockito.when(update.hasCallbackQuery()).thenReturn(true);
        Mockito.when(callbackQuery.getData()).thenReturn(wordName);
        SendMessage sendMessage = (SendMessage) dictionaryBot.onWebhookUpdateReceived(update);
        assertEquals("Слово не найдено. Проверьте, пожалуйста, верно ли поставлен режим работы бота",
                sendMessage.getText());
    }



    public static Collection<Object[]> testPolishWords() {
        return Arrays.asList(new Object[][]{
                {"czytać"},
                {"miasto"}
        });
    }
}

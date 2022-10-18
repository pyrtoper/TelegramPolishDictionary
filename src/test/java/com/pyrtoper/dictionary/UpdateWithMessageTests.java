package com.pyrtoper.dictionary;

import static org.junit.jupiter.api.Assertions.*;
import com.pyrtoper.dictionary.bot.DictionaryBot;
import com.pyrtoper.dictionary.constant.WorkState;
import com.pyrtoper.dictionary.entity.PolishWord;
import com.pyrtoper.dictionary.entity.Translation;
import com.pyrtoper.dictionary.entity.WorkStates;
import com.pyrtoper.dictionary.repository.WorkStateRepository;
import com.pyrtoper.dictionary.service.DisplayToTelegramService;
import com.pyrtoper.dictionary.service.PolishWordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.Collection;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = DictionaryApplication.class)
@ExtendWith(MockitoExtension.class)
@Testcontainers
public class UpdateWithMessageTests {
    @Mock
    private Message message;
    @Mock
    private Update update;
    private PolishWordService polishWordService;
    private DictionaryBot dictionaryBot;
    private WorkStateRepository workStateRepository;
    private DisplayToTelegramService displayToTelegramService;
    private final long chatId = 1L;

    @Autowired
    public void setWordService(PolishWordService polishWordService,
        DictionaryBot dictionaryBot,
        WorkStateRepository workStateRepository,
        DisplayToTelegramService displayToTelegramService) {
        this.polishWordService = polishWordService;
        this.dictionaryBot = dictionaryBot;
        this.workStateRepository = workStateRepository;
        this.displayToTelegramService = displayToTelegramService;
    }

    @BeforeEach
    void setup() {
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(update.getMessage()).thenReturn(message);
        Mockito.when(update.hasMessage()).thenReturn(true);
    }

    @ParameterizedTest
    @MethodSource("testPolishWords")
    public void checkSendingMessageWithExactPolishWord_CorrectState(String wordName) {
        Mockito.when(message.getText()).thenReturn(wordName);
        workStateRepository.save(new WorkStates(chatId));
        PolishWord polishWord = polishWordService.getWordByName(wordName).get();
        SendMessage sendMessage = (SendMessage) dictionaryBot.onWebhookUpdateReceived(update);
        assertEquals(displayToTelegramService.displayPolishWord(polishWord), sendMessage.getText());
    }
    @ParameterizedTest
    @MethodSource("testPolishWords")
    public void checkSendingMessageWithExactPolishWord_WrongState_shouldGetSimilarity(String wordName) {
        Mockito.when(message.getText()).thenReturn(wordName);
        workStateRepository.save(new WorkStates(chatId, WorkState.RUSSIAN_TO_POLISH));
        SendMessage sendMessage = (SendMessage) dictionaryBot.onWebhookUpdateReceived(update);
        assertEquals("Возможно, Вы имели ввиду:\n", sendMessage.getText());
    }

    @ParameterizedTest
    @MethodSource("testRussianWords")
    public void checkSendingMessageWithExactRussianWord_CorrectState(String wordName) {
        Mockito.when(message.getText()).thenReturn(wordName);
        workStateRepository.save(new WorkStates(chatId, WorkState.RUSSIAN_TO_POLISH));
        Translation translation = polishWordService.getTranslationByName(wordName).get();
        SendMessage sendMessage = (SendMessage) dictionaryBot.onWebhookUpdateReceived(update);
        assertEquals(displayToTelegramService.displayTranslation(translation), sendMessage.getText());
    }

    @ParameterizedTest
    @MethodSource("testRussianWords")
    public void checkSendingMessageWithExactRussianWord_WrongState_shouldGetSimilarity(String wordName) {
        Mockito.when(message.getText()).thenReturn(wordName);
        workStateRepository.save(new WorkStates(chatId, WorkState.POLISH_TO_RUSSIAN));
        SendMessage sendMessage = (SendMessage) dictionaryBot.onWebhookUpdateReceived(update);
        assertEquals("Возможно, Вы имели ввиду:\n", sendMessage.getText());
    }

    public static Collection<Object[]> testPolishWords() {
        return Arrays.asList(new Object[][]{
            {"paralela"},
            {"bojkocik"},
            {"Palermo"},
            {"kostropaty"},
            {"koedukacyjność"}
        });
    }

    public static Collection<Object[]> testRussianWords() {
        return Arrays.asList(new Object[][]{
            {"смеяться"},
            {"гербарий"},
            {"расстроенный"},
            {"бойкот"},
            {"четырёхкомнатный"}
        });
    }
}

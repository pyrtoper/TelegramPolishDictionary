package com.pyrtoper.dictionary;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pyrtoper.dictionary.bot.DictionaryBot;
import com.pyrtoper.dictionary.constant.WorkState;
import com.pyrtoper.dictionary.entity.PolishWord;
import com.pyrtoper.dictionary.entity.Translation;
import com.pyrtoper.dictionary.entity.WorkStates;
import com.pyrtoper.dictionary.repository.WorkStateRepository;
import com.pyrtoper.dictionary.service.DisplayToTelegramService;
import com.pyrtoper.dictionary.service.MissingWordService;
import com.pyrtoper.dictionary.service.PolishWordService;
import java.util.Arrays;
import java.util.Collection;
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
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = DictionaryApplication.class)
@ExtendWith(MockitoExtension.class)
@Testcontainers
public class UpdateWithCallbackQueryTest {
  @Mock
  private Update update;
  @Mock
  private CallbackQuery callbackQuery;
  @Mock
  private Message message;
  private final PolishWordService polishWordService;
  private final DictionaryBot dictionaryBot;
  private final WorkStateRepository workStateRepository;
  private final DisplayToTelegramService displayToTelegramService;

  private final MissingWordService missingWordService;

  private final long chatId = 1L;

  @Autowired
  public UpdateWithCallbackQueryTest(DictionaryBot dictionaryBot,
      WorkStateRepository workStateRepository,
      DisplayToTelegramService displayToTelegramService,
      PolishWordService polishWordService,
      MissingWordService missingWordService) {
    this.dictionaryBot = dictionaryBot;
    this.workStateRepository = workStateRepository;
    this.displayToTelegramService = displayToTelegramService;
    this.polishWordService = polishWordService;
    this.missingWordService = missingWordService;
  }

  @BeforeEach
  void setup() {
    Mockito.when(update.hasCallbackQuery()).thenReturn(true);
    Mockito.when(message.getChatId()).thenReturn(chatId);
    Mockito.when(callbackQuery.getMessage()).thenReturn(message);
    Mockito.when(update.getCallbackQuery()).thenReturn(callbackQuery);
  }

  @ParameterizedTest
  @MethodSource("testPolishWords")
  public void checkPushingButtonWithExactPolishWord_CorrectState(String inlineButtonText) {
    Mockito.when(callbackQuery.getData()).thenReturn(inlineButtonText);
    workStateRepository.save(new WorkStates(chatId, WorkState.POLISH_TO_RUSSIAN));
    PolishWord polishWord = polishWordService.getWordByName(inlineButtonText).get();
    SendMessage sendMessage = (SendMessage) dictionaryBot.onWebhookUpdateReceived(update);
    assertEquals(displayToTelegramService.displayPolishWord(polishWord),
        sendMessage.getText());
  }

  @ParameterizedTest
  @MethodSource("testPolishWords")
  public void checkPushingButtonWithExactPolishWord_WrongState(String inlineButtonText) {
    Mockito.when(callbackQuery.getData()).thenReturn(inlineButtonText);
    workStateRepository.save(new WorkStates(chatId, WorkState.RUSSIAN_TO_POLISH));
    SendMessage sendMessage = (SendMessage) dictionaryBot.onWebhookUpdateReceived(update);
    assertEquals("Слово не найдено. Проверьте, пожалуйста, верно ли поставлен режим работы бота",
        sendMessage.getText());
  }

  @ParameterizedTest
  @MethodSource("testRussianWords")
  public void checkPushingButtonWithExactRussianWord_CorrectState(String inlineButtonText) {
    Mockito.when(callbackQuery.getData()).thenReturn(inlineButtonText);
    workStateRepository.save(new WorkStates(chatId, WorkState.RUSSIAN_TO_POLISH));
    Translation translation = polishWordService.getTranslationByName(inlineButtonText).get();
    SendMessage sendMessage = (SendMessage) dictionaryBot.onWebhookUpdateReceived(update);
    assertEquals(displayToTelegramService.displayTranslation(translation),
        sendMessage.getText());
  }

  @ParameterizedTest
  @MethodSource("testRussianWords")
  public void checkPushingButtonWithExactRussianWord_WrongState(String inlineButtonText) {
    Mockito.when(callbackQuery.getData()).thenReturn(inlineButtonText);
    workStateRepository.save(new WorkStates(chatId, WorkState.POLISH_TO_RUSSIAN));
    SendMessage sendMessage = (SendMessage) dictionaryBot.onWebhookUpdateReceived(update);
    assertEquals("Слово не найдено. Проверьте, пожалуйста, верно ли поставлен режим работы бота",
        sendMessage.getText());
  }

  @ParameterizedTest
  @MethodSource("testMissingWords")
  public void shouldAddToMissingWordsTable(String wordName) {
    Mockito.when(callbackQuery.getData()).thenReturn("Мое слово - " + wordName);
    workStateRepository.save(new WorkStates(chatId, WorkState.POLISH_TO_RUSSIAN));
    SendMessage sendMessage = (SendMessage) dictionaryBot.onWebhookUpdateReceived(update);
    assertDoesNotThrow(() -> missingWordService.getMissingWordByName(wordName).get());
    assertEquals("Слово не найдено. "
        + "Сожалею, что так произошло, мой создатель уже оповещен об этом недоразумении!",
        sendMessage.getText());
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

  public static Collection<Object[]> testMissingWords() {
    return Arrays.asList(new Object[][]{
        {"сохранять"},
        {"сохранять"},
        {"сохранять"},
        {"praktykować"},
        {"hasdashd"},
    });
  }
}

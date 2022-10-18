package com.pyrtoper.dictionary;

import static org.junit.jupiter.api.Assertions.*;

import com.pyrtoper.dictionary.entity.MissingWord;
import com.pyrtoper.dictionary.entity.PolishWord;
import com.pyrtoper.dictionary.entity.Translation;
import com.pyrtoper.dictionary.exception.WordIsMissingException;
import com.pyrtoper.dictionary.repository.MissingWordRepository;
import com.pyrtoper.dictionary.service.DisplayToTelegramService;
import com.pyrtoper.dictionary.service.MissingWordService;
import com.pyrtoper.dictionary.service.PolishWordService;
import com.pyrtoper.dictionary.service.impl.PolishToRussian;
import com.pyrtoper.dictionary.service.impl.RussianToPolish;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public class ServiceLayerTest {

  private final PolishWordService polishWordService;
  private final MissingWordService missingWordService;
  private final DisplayToTelegramService displayToTelegramService;
  private final RussianToPolish russianToPolish;
  private final PolishToRussian polishToRussian;

  private final MissingWordRepository missingWordRepository;

  @Autowired
  public ServiceLayerTest(PolishWordService polishWordService,
      MissingWordService missingWordService,
      DisplayToTelegramService displayToTelegramService, RussianToPolish russianToPolish,
      PolishToRussian polishToRussian,
      MissingWordRepository missingWordRepository) {
    this.polishWordService = polishWordService;
    this.missingWordService = missingWordService;
    this.displayToTelegramService = displayToTelegramService;
    this.russianToPolish = russianToPolish;
    this.polishToRussian = polishToRussian;
    this.missingWordRepository = missingWordRepository;
  }

  @Test
  public void shouldThrowWordIsMissingExceptionPolishToRussian() {
    assertThrows(WordIsMissingException.class, () -> polishToRussian.translate("чихать"));
  }

  @Test
  public void shouldThrowWordIsMissingExceptionRussianToPolish() {
    assertThrows(WordIsMissingException.class, () -> russianToPolish.translate("antybermudzki"));
  }

  @Test
  public void shouldSaveNewMissingWord() {
    MissingWord missingWord = missingWordService.saveMissingWord("сфлорфсф");
    assertEquals(missingWord, missingWordService.getMissingWordByName(missingWord.getWordName()).get());
  }

  @Test
  public void shouldNotSaveTheSameMissingWord() {
    MissingWord missingWordInit = missingWordService.saveMissingWord("лоиывлавы");
    MissingWord missingWordAnother = missingWordService.saveMissingWord(missingWordInit.getWordName());
    assertEquals(missingWordInit, missingWordAnother);
  }

  @ParameterizedTest
  @MethodSource("testDisplayToTelegramPolishWords")
  public void shouldDisplayPolishWordToTelegramCorrectly(String wordName, String displayToTelegram) {
    PolishWord polishWord = polishWordService.getWordByName(wordName).get();
    assertEquals(displayToTelegram, displayToTelegramService.displayPolishWord(polishWord));
  }

  @ParameterizedTest
  @MethodSource("testDisplayToTelegramRussianWords")
  public void shouldDisplayRussianWordToTelegramCorrectly(String wordName, String displayToTelegram) {
    Translation translation = polishWordService.getTranslationByName(wordName).get();
    assertEquals(displayToTelegram, displayToTelegramService.displayTranslation(translation));
  }

  @Test
  public void shouldThrowIncorrectResultSizeDataAccessExceptionOnMissingWord() {
    missingWordRepository.saveAndFlush(new MissingWord("svsdfsd", LocalDateTime.now()));
    missingWordRepository.saveAndFlush(new MissingWord("svsdfsd", LocalDateTime.now()));

    assertThrows(IncorrectResultSizeDataAccessException.class,
        () -> missingWordService.getMissingWordByName("svsdfsd")
        );
  }

  public static Collection<Object[]> testDisplayToTelegramPolishWords() {
    return Arrays.asList(new Object[][]{
        {"szarpnyć sie", "Слово: szarpnyć sie\n"
            + "\uD83D\uDC49Значения:\n"
            + "\uD83D\uDD39глаг. возвр.\n"
            + "    ▪(1.1) (диалект: Тешинская Силезия) будь щедрым"},
        {"kontrkulturowość", "Слово: kontrkulturowość\n"
            + "\uD83D\uDC49Значения:\n"
            + "\uD83D\uDD39сущ., род ж.\n"
            + "    ▪(1.1) особенность того, что является контркультурным\n"
            + "\uD83D\uDC49Формы слова:\n"
            + "(1.1):\n"
            + "    ед. ч:\n"
            + "        Mianownik: kontrkulturowość\n"
            + "        Dopełniacz: kontrkulturowości\n"
            + "        Celownik: kontrkulturowości\n"
            + "        Biernik: kontrkulturowość\n"
            + "        Narzędnik: kontrkulturowością\n"
            + "        Miejscownik: kontrkulturowości\n"
            + "        Wołacz: kontrkulturowości"},
        {"AWPL", "Слово: AWPL\n"
            + "\uD83D\uDC49Значения:\n"
            + "\uD83D\uDD39аббрев. w funkcji rzeczownika rodzaju nijakiego, имя собств.\n"
            + "    ▪(1.1) = избирательная акция поляков в Литве;"},
        {"kto z Bogiem zacznie, uczyni bacznie", "Слово: kto z Bogiem zacznie, uczyni bacznie\n"
            + "\uD83D\uDC49Значения:\n"
            + "\uD83D\uDD39пословица\n"
            + "    ▪(1.1) хорошо и мудро все начинания начинать с призыва помощи Божией"},
        {"helsiński", "Слово: helsiński\n"
            + "\uD83D\uDC49Значения:\n"
            + "\uD83D\uDD39прилаг. относит.\n"
            + "    ▪(1.1) в отношении города Хельсинки\n"
            + "\uD83D\uDC49Возможный перевод на русский: хельсинкский"},
        {"krokodyli", "Слово: krokodyli\n"
            + "\uD83D\uDC49Значения:\n"
            + "\uD83D\uDD39прилаг. dzierżawczy\n"
            + "    ▪(1.1) принадлежащий крокодилу\n"
            + "\uD83D\uDD39прилаг. качеств.\n"
            + "    ▪(2.1) характеристика крокодила, обладающая характеристиками крокодила\n"
            + "\uD83D\uDD39форма сущ-го\n"
            + "    ▪(3.1) множественное число (от :) крокодил"},
        {"bojkocik", "Слово: bojkocik\n"
            + "\uD83D\uDC49Значения:\n"
            + "\uD83D\uDD39сущ., род вещно-м.\n"
            + "    ▪(1.1) (де) бойкот\n"
            + "\uD83D\uDC49Формы слова:\n"
            + "(1.1):\n"
            + "    ед. ч:\n"
            + "        Mianownik: bojkocik\n"
            + "        Dopełniacz: bojkociku\n"
            + "        Celownik: bojkocikowi\n"
            + "        Biernik: bojkocik\n"
            + "        Narzędnik: bojkocikiem\n"
            + "        Miejscownik: bojkociku\n"
            + "        Wołacz: bojkociku\n"
            + "    мн. ч:\n"
            + "        Mianownik: bojkociki\n"
            + "        Dopełniacz: bojkocików\n"
            + "        Celownik: bojkocikom\n"
            + "        Biernik: bojkociki\n"
            + "        Narzędnik: bojkocikami\n"
            + "        Miejscownik: bojkocikach\n"
            + "        Wołacz: bojkociki\n"
            + "\uD83D\uDC49Возможный перевод на русский: бойкот"}
    });
  }

  public static Collection<Object[]> testDisplayToTelegramRussianWords() {
    return Arrays.asList(new Object[][]{
        {"смеяться", "\uD83D\uDC49Возможный перевод на польский:\n"
            + "parsknąć"},
        {"пешеходная зона", "\uD83D\uDC49Возможный перевод на польский:\n"
            + "deptaczek"},
        {"факоэмульсификация", "\uD83D\uDC49Возможный перевод на польский:\n"
            + "fakoemulsyfikacja"},
        {"Валерий", "\uD83D\uDC49Возможный перевод на польский:\n"
            + "Waleriusz"},
        {"бойкот", "\uD83D\uDC49Возможный перевод на польский:\n"
            + "bojkocik"},
        {"концертный", "\uD83D\uDC49Возможный перевод на польский:\n"
            + "koncertowy"},
        {"Палермо", "\uD83D\uDC49Возможный перевод на польский:\n"
            + "Palermo"}
    });
  }
}

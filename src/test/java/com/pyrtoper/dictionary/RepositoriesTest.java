package com.pyrtoper.dictionary;

import static org.junit.jupiter.api.Assertions.*;
import com.pyrtoper.dictionary.entity.PolishWord;
import com.pyrtoper.dictionary.entity.Translation;
import com.pyrtoper.dictionary.repository.PolishWordRepository;
import com.pyrtoper.dictionary.repository.TranslationRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class RepositoriesTest {

  private final PolishWordRepository polishWordRepository;
  private final TranslationRepository translationRepository;

  @Autowired
  public RepositoriesTest(PolishWordRepository polishWordRepository,
      TranslationRepository translationRepository) {
    this.polishWordRepository = polishWordRepository;
    this.translationRepository = translationRepository;
  }

  @Test
  public void shouldGetPolishWordByName() {
    Optional<PolishWord> wordInDB = polishWordRepository.getPolishWordByName("koncertowy");
    assertEquals("koncertowy", wordInDB.get().getName());
  }

  @Test
  public void shouldGetRussianWordByName() {
    Optional<Translation> wordInDB = translationRepository.getTranslationByName("смеяться");
    assertEquals("смеяться", wordInDB.get().getName());
  }

  @Test
  public void shouldGetNothingToInitialPolishWord() {
    Optional<PolishWord> wordInDB = polishWordRepository.getPolishWordByName("nasldksa");
    assertEquals(Optional.empty(), wordInDB);
  }

  @Test
  public void shouldGetNothingToInitialRussianWord() {
    Optional<Translation> wordInDB = translationRepository.getTranslationByName("вдфлырвыфв");
    assertEquals(Optional.empty(), wordInDB);
  }

  @Test
  public void shouldGetSimilarWordsToPolishWord() {
    List<String> similarToInitialWord = polishWordRepository.getSimilarWords("helsińki");
    assertEquals(List.of("helsiński", "herbarz", "holizm", "antybermudzki", "kontrkulturowość"),
        similarToInitialWord);
  }

  @Test
  public void shouldGetSimilarWordsToRussianWord() {
    List<String> similarToInitialWord = translationRepository.getSimilarTranslations("герба");
    assertEquals(List.of("гербарий", "гербовник", "гербарий , травник", "чихать", "факоэмульсификация"),
        similarToInitialWord);
  }

}

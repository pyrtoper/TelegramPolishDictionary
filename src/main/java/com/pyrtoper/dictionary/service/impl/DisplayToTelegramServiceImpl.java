package com.pyrtoper.dictionary.service.impl;

import com.pyrtoper.dictionary.constant.Declination;
import com.pyrtoper.dictionary.constant.Numeral;
import com.pyrtoper.dictionary.entity.PolishWord;
import com.pyrtoper.dictionary.entity.Translation;
import com.pyrtoper.dictionary.service.DisplayToTelegramService;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DisplayToTelegramServiceImpl implements DisplayToTelegramService {

  @Override
  public String displayPolishWord(PolishWord word) {
    StringBuilder result = new StringBuilder();
    result.append("Слово: ");
    result.append(word.getName());
    result.append("\n" + "\uD83D\uDC49" +  "Значения:");
    String prefixSpecification = "\n";
    String prefixMeaning = "\n    ";

    for (Map.Entry<String, List<String>> entry: word
        .getTranslatedMeanings().getTranslatedMeaning().entrySet()
        .stream()
        .sorted(Comparator.comparing(entry -> entry.getValue().stream()
            .filter(Predicate.not(String::isEmpty))
            .findFirst()
            .orElse("")))
        .collect(Collectors.toList())) {
      result.append(prefixSpecification);
      result.append("\uD83D\uDD39");
      result.append(entry.getKey());
      for (String meaning: entry.getValue().stream()
          .filter(Predicate.not(String::isEmpty))
          .sorted()
          .collect(Collectors.toList())) {
        result.append(prefixMeaning);
        result.append("\u25AA");
        result.append(meaning);
      }
    }
    StringBuilder tempStringBuilder = new StringBuilder();
    if (!word.getWordForms().getWordForms().isEmpty()) {
      tempStringBuilder.append("\n\uD83D\uDC49Формы слова:\n");
      String prefixGeneral = "";
      String prefixNumeral = "\n    ";
      String prefixDeclination = "\n        ";
      for (Map.Entry<String, Map<Numeral, Map<Declination, String>>> generalEntry:
          sortedEntrySet(word.getWordForms().getWordForms())) {
        if (generalEntry.getValue().isEmpty()) {
          continue;
        }
        tempStringBuilder.append(prefixGeneral);
        tempStringBuilder.append(generalEntry.getKey());
        tempStringBuilder.append(":");
        prefixGeneral = "\n";
        for (Map.Entry<Numeral, Map<Declination, String>> numeralEntry: sortedEntrySet(generalEntry.getValue())) {
          tempStringBuilder.append(prefixNumeral);
          tempStringBuilder.append(numeralEntry.getKey().getRussianNumeral());
          tempStringBuilder.append(":");
          for (Map.Entry<Declination, String> declinationEntry: sortedEntrySet(numeralEntry.getValue())) {
            tempStringBuilder.append(prefixDeclination);
            tempStringBuilder.append(declinationEntry.getKey().getName());
            tempStringBuilder.append(": ");
            tempStringBuilder.append(declinationEntry.getValue());
          }
        }
      }
    }
    if (!tempStringBuilder.toString().equals("\n\uD83D\uDC49Формы слова:\n")) {
      result.append(tempStringBuilder);
    }
    if (!word.getTranslationSet().isEmpty()) {
      result.append("\n\uD83D\uDC49Возможный перевод на русский: ");
      String prefix = "";
      for (Translation translation: word.getTranslationSet()) {
        result.append(prefix);
        result.append(translation.getName());
        prefix = ", ";
      }
    }
    return result.toString();
  }

  @Override
  public String displayTranslation(Translation translation) {
    StringBuilder result = new StringBuilder();
    if (translation.getWordSet().isEmpty()) {
      result.append("К сожалению, на польский перевести не удалось \uD83D\uDE29." +
          "Мой создатель уже оповещен об этом!");
    } else {
      result.append("\uD83D\uDC49Возможный перевод на польский:\n");
      String prefix = "";
      for (PolishWord polishWord : translation.getWordSet()) {
        result.append(prefix);
        result.append(polishWord.getName());
        prefix = ", ";
      }
    }
    return result.toString();
  }

  private <K extends Comparable<? super K>, V> List<Map.Entry<K, V>> sortedEntrySet(Map<K, V> map) {
    return map.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByKey())
        .collect(Collectors.toList());
  }
}

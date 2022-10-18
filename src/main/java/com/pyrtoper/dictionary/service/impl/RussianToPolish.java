package com.pyrtoper.dictionary.service.impl;

import com.pyrtoper.dictionary.constant.WorkState;
import com.pyrtoper.dictionary.entity.Translation;
import com.pyrtoper.dictionary.exception.WordIsMissingException;
import com.pyrtoper.dictionary.service.PolishWordService;
import com.pyrtoper.dictionary.service.WorkStateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RussianToPolish implements WorkStateService {
  private final PolishWordService polishWordService;
  private final DisplayToTelegramServiceImpl displayToTelegramService;
  private final WorkState workState = WorkState.RUSSIAN_TO_POLISH;

  @Autowired
  public RussianToPolish(PolishWordService polishWordService,
      DisplayToTelegramServiceImpl displayToTelegramService) {
    this.polishWordService = polishWordService;
    this.displayToTelegramService = displayToTelegramService;
  }

  @Override
  @Transactional(readOnly = true)
  public String translate(String word) throws WordIsMissingException {
    Translation translation = polishWordService.getTranslationByName(word).orElseThrow(
        () -> new WordIsMissingException("Russian word " + word + " is missing")
    );
    return displayToTelegramService.displayTranslation(translation);
  }

  @Override
  public List<String> getSimilarWords(String initialWord) {
    return polishWordService.getSimilarTranslations(initialWord);
  }

  @Override
  public WorkState getType() {
    return workState;
  }
}

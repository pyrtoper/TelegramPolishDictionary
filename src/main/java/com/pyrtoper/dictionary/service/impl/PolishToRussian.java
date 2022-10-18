package com.pyrtoper.dictionary.service.impl;

import com.pyrtoper.dictionary.constant.WorkState;
import com.pyrtoper.dictionary.entity.PolishWord;
import com.pyrtoper.dictionary.exception.WordIsMissingException;
import com.pyrtoper.dictionary.service.PolishWordService;
import com.pyrtoper.dictionary.service.WorkStateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PolishToRussian implements WorkStateService {

  private final PolishWordService polishWordService;
  private final DisplayToTelegramServiceImpl displayToTelegramService;

  private final WorkState workState = WorkState.POLISH_TO_RUSSIAN;

  @Autowired
  public PolishToRussian(PolishWordService polishWordService,
      DisplayToTelegramServiceImpl displayToTelegramService) {
    this.polishWordService = polishWordService;
    this.displayToTelegramService = displayToTelegramService;
  }
  @Override
  @Transactional(readOnly = true)
  public String translate(String word) throws WordIsMissingException {
    PolishWord polishWord = polishWordService.getWordByName(word).orElseThrow(
        () -> new WordIsMissingException("Polish word " + word + "is missing!")
    );
    return displayToTelegramService.displayPolishWord(polishWord);
  }

  @Override
  public List<String> getSimilarWords(String initialWord) {
    return polishWordService.getSimilarPolishWords(initialWord);
  }

  @Override
  public WorkState getType() {
    return workState;
  }
}

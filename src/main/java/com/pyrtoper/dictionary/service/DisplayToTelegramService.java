package com.pyrtoper.dictionary.service;

import com.pyrtoper.dictionary.entity.PolishWord;
import com.pyrtoper.dictionary.entity.Translation;

public interface DisplayToTelegramService {

  String displayPolishWord(PolishWord word);
  String displayTranslation(Translation translation);
}

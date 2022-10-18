package com.pyrtoper.dictionary.service;

import com.pyrtoper.dictionary.entity.MissingWord;
import java.util.Optional;

public interface MissingWordService {

  MissingWord saveMissingWord(String missingWordName);

  Optional<MissingWord> getMissingWordByName(String wordName);

}

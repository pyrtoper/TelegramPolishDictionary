package com.pyrtoper.dictionary.service;

import com.pyrtoper.dictionary.dao.WordDAO;
import com.pyrtoper.dictionary.entity.Translation;
import com.pyrtoper.dictionary.entity.Word;

import java.util.List;

public interface WordService {
    Word getWordByName(String wordName);

    Translation getWordByTranslation(String translation);

    List<String> getSimilarWords(String initialWord);
}

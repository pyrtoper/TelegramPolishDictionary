package com.pyrtoper.dictionary.service;

import com.pyrtoper.dictionary.dao.WordDAO;
import com.pyrtoper.dictionary.entity.Translation;
import com.pyrtoper.dictionary.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class WordServiceImpl implements WordService {

    @Autowired
    private WordDAO wordDAO;

    @Override
    @Transactional
    public Word getWordByName(String wordName) {
        return wordDAO.getWordByName(wordName);
    }

    @Override
    public Translation getWordByTranslation(String translation) {
        return null;
    }

    @Override
    @Transactional
    public List<String> getSimilarWords(String initialWord) {
        return wordDAO.getSimilarWords(initialWord);
    }
}

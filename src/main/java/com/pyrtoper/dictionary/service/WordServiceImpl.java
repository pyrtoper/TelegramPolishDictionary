package com.pyrtoper.dictionary.service;

import com.pyrtoper.dictionary.dao.MissingWordDAO;
import com.pyrtoper.dictionary.dao.TranslationDAO;
import com.pyrtoper.dictionary.dao.WordDAO;
import com.pyrtoper.dictionary.entity.MissingWord;
import com.pyrtoper.dictionary.entity.Translation;
import com.pyrtoper.dictionary.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class WordServiceImpl implements WordService {

    @Autowired
    private WordDAO wordDAO;

    @Autowired
    private TranslationDAO translationDAO;

    @Autowired
    private MissingWordDAO missingWordDAO;

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



    @Override
    @Transactional
    public Translation getTranslationByName(String translationName) {
        return translationDAO.getTranslationByName(translationName);
    }

    @Override
    @Transactional
    public List<String> getSimilarTranslations(String initialWord) {
        return translationDAO.getSimilarTranslations(initialWord);
    }

    @Override
    @Transactional
    public void saveMissingWord(MissingWord missingWord) {
        missingWordDAO.saveMissingWord(missingWord);
    }

    @Override
    @Transactional
    public MissingWord getMissingWordByName(String wordName) {
        return missingWordDAO.getMissingWordByName(wordName);
    }
}

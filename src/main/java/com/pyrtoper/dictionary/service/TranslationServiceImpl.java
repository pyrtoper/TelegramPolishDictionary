package com.pyrtoper.dictionary.service;

import com.pyrtoper.dictionary.dao.TranslationDAO;
import com.pyrtoper.dictionary.entity.Translation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

public class TranslationServiceImpl implements TranslationService {

    @Autowired
    private TranslationDAO translationDAO;

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
}

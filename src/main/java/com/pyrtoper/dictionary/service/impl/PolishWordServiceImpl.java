package com.pyrtoper.dictionary.service.impl;

import com.pyrtoper.dictionary.entity.Translation;
import com.pyrtoper.dictionary.entity.PolishWord;
import com.pyrtoper.dictionary.repository.PolishWordRepository;
import com.pyrtoper.dictionary.repository.TranslationRepository;
import com.pyrtoper.dictionary.service.PolishWordService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PolishWordServiceImpl implements PolishWordService {

    private final PolishWordRepository polishWordRepository;

    private final TranslationRepository translationRepository;
    @Autowired
    public PolishWordServiceImpl(PolishWordRepository polishWordRepository,
        TranslationRepository translationRepository) {
        this.polishWordRepository = polishWordRepository;
        this.translationRepository = translationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PolishWord> getWordByName(String wordName) {
        return polishWordRepository.getPolishWordByName(wordName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getSimilarPolishWords(String initialWord) {
        return polishWordRepository.getSimilarWords(initialWord);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Translation> getTranslationByName(String translationName) {
        return translationRepository.getTranslationByName(translationName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getSimilarTranslations(String initialWord) {
        return translationRepository.getSimilarTranslations(initialWord);
    }

}

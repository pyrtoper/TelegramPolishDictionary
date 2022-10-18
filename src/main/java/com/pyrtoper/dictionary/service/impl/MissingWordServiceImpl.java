package com.pyrtoper.dictionary.service.impl;

import com.pyrtoper.dictionary.entity.MissingWord;
import com.pyrtoper.dictionary.exception.WordIsMissingException;
import com.pyrtoper.dictionary.repository.MissingWordRepository;
import com.pyrtoper.dictionary.service.MissingWordService;

import java.time.LocalDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MissingWordServiceImpl implements MissingWordService {

  private final MissingWordRepository missingWordRepository;

  private static final Logger logger = LoggerFactory.getLogger(MissingWordServiceImpl.class.getName());

  @Autowired
  public MissingWordServiceImpl(MissingWordRepository missingWordRepository) {
    this.missingWordRepository = missingWordRepository;
  }

  @Override
  @Transactional
  public MissingWord saveMissingWord(String missingWordName) {
    try {
      return getMissingWordByName(missingWordName).orElseThrow(
          () -> new WordIsMissingException("Word was not found in DB")
      );
    } catch (WordIsMissingException e) {
      logger.debug(e.toString());
      MissingWord missingWord = new MissingWord();
      missingWord.setWordName(missingWordName);
      missingWord.setLocalDateTime(LocalDateTime.now());
      missingWordRepository.saveAndFlush(missingWord);
      return missingWord;
    } catch (IncorrectResultSizeDataAccessException e) {
      logger.error("Word " + missingWordName +
          " is already exists in MissingWords DB");
      return null;
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<MissingWord> getMissingWordByName(String wordName) {
    return missingWordRepository.getMissingWordByName(wordName);
  }
}

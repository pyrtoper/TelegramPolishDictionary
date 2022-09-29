package com.pyrtoper.dictionary.dao;

import com.pyrtoper.dictionary.entity.MissingWord;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public interface MissingWordDAO {

    void saveMissingWord(MissingWord missingWord);

    List<MissingWord> getAllMissingWords();

    MissingWord getMissingWordByName(String wordName);
}
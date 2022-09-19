package com.pyrtoper.dictionary.dao;

import com.pyrtoper.dictionary.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class PolishWordDAO implements WordDAO {
    @Autowired
    private EntityManager entityManager;



    @Override
    public Word getWordByName(String wordName) {
        TypedQuery<Word> query = entityManager
                .createQuery("from Word w where w.name=:wordName", Word.class);
        query.setParameter("wordName", wordName);
        Word word = query.getSingleResult();
        return word;
    }

    @Override
    public Word getTranslationByName(String translation) {
        return null;
    }

    @Override
    public List<String> getSimilarWords(String initialWord) {
        Query query = entityManager
                .createNativeQuery("select name from words order by similarity(name, :initialWord) desc limit 5");
//        Query query = entityManager
//                .createNamedQuery("similarityQuery", String.class);
        query.setParameter("initialWord", initialWord);
        return query.getResultList();
    }
}

package com.pyrtoper.dictionary.dao;

import com.pyrtoper.dictionary.entity.Translation;
import com.pyrtoper.dictionary.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Repository
public class PolishWordDAO implements WordDAO {
    @Autowired
    private EntityManager entityManager;

    @Override
    public Word getWordByName(String wordName) {
        EntityGraph<Word> entityGraph = entityManager.createEntityGraph(Word.class);
        entityGraph.addAttributeNodes("translatedMeanings", "wordForms", "translationSet");
        TypedQuery<Word> query = entityManager
                .createQuery("select w from Word w where w.name=:wordName", Word.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        query.setParameter("wordName", wordName);
        Word word = query.getSingleResult();
        return word;
    }

    @Override
    public Set<Translation> getTranslationByName(String translation) {
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

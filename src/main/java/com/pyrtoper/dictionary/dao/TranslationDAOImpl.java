package com.pyrtoper.dictionary.dao;

import com.pyrtoper.dictionary.entity.Translation;
import com.pyrtoper.dictionary.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

@Repository
public class TranslationDAOImpl implements TranslationDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Translation getTranslationByName(String translationName) {
        EntityGraph<Translation> entityGraph = entityManager.createEntityGraph(Translation.class);
        entityGraph.addAttributeNodes("wordSet");
        TypedQuery<Translation> query = entityManager
                .createQuery("from Translation where name = :translationName", Translation.class);
        query.setParameter("translationName", translationName);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getSingleResult();
    }

    @Override
    public List<String> getSimilarTranslations(String initialWord) {
        Query query = entityManager
                .createNativeQuery("select name from translations order by similarity(name, :initialWord) desc limit 5");
//        Query query = entityManager
//                .createNamedQuery("similarityQuery", String.class);
        query.setParameter("initialWord", initialWord);
        return query.getResultList();
    }

    @Override
    public Set<Word> getPolishTranslations() {
        return null;
    }
}

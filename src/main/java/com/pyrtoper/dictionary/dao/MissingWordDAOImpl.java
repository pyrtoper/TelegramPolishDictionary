package com.pyrtoper.dictionary.dao;

import com.pyrtoper.dictionary.entity.MissingWord;
import com.pyrtoper.dictionary.exception.WordIsMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.lang.invoke.WrongMethodTypeException;
import java.util.List;

@Repository
public class MissingWordDAOImpl implements MissingWordDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void saveMissingWord(MissingWord missingWord) {
        entityManager.persist(missingWord);
        entityManager.flush();
    }

    @Override
    public List<MissingWord> getAllMissingWords() {
        TypedQuery<MissingWord> query = entityManager
                .createQuery("from MissingWord", MissingWord.class);
        return query.getResultList();
    }

    @Override
    public MissingWord getMissingWordByName(String wordName) {
        List<MissingWord> wordFromDB = entityManager
                .createQuery("from MissingWord where wordName = :missingWord", MissingWord.class)
                .setParameter("missingWord", wordName)
                .getResultList();
        if (wordFromDB.isEmpty()) {
            throw new WordIsMissingException("Such word was not found in DB");
        }
        return wordFromDB.get(0);
    }
}

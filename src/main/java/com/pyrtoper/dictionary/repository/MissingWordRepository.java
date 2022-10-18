package com.pyrtoper.dictionary.repository;

import com.pyrtoper.dictionary.entity.MissingWord;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissingWordRepository extends JpaRepository<MissingWord, Integer> {

  @Query(value = "from MissingWord where wordName = :missingWord")
  Optional<MissingWord> getMissingWordByName(@Param("missingWord") String wordName);
}

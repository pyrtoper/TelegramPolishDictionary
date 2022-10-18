package com.pyrtoper.dictionary.repository;

import com.pyrtoper.dictionary.entity.PolishWord;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PolishWordRepository extends JpaRepository<PolishWord, Integer> {

  @Query(value = "select name from words order by similarity(name, :initialWord) desc limit 5",
  nativeQuery = true)
  List<String> getSimilarWords(@Param("initialWord") String initialWord);

  @Query(value = "select w from PolishWord w where w.name=:wordName")
  @EntityGraph(value = "graph.Word.meanings.wordForms.translations")
  Optional<PolishWord> getPolishWordByName(@Param("wordName") String wordName);
}

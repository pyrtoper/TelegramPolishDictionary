package com.pyrtoper.dictionary.repository;

import com.pyrtoper.dictionary.entity.Translation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TranslationRepository extends JpaRepository<Translation, Integer> {

  @Query(value = "from Translation where name = :translationName")
  @EntityGraph(value = "graph.Translation.wordSet")
  Optional<Translation> getTranslationByName(String translationName);

  @Query(value = "select name from translations order by similarity(name, :initialWord) desc limit 5",
  nativeQuery = true)
  List<String> getSimilarTranslations(String initialWord);
}

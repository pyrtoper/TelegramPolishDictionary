package com.pyrtoper.dictionary.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Entity
@Table(name = "meanings")
public class TranslatedMeaning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "meanings_jsonb")
    @Type(type = "com.vladmihalcea.hibernate.type.json.JsonType")
    private Map<String, List<String>> translatedMeaning;

    public TranslatedMeaning() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, List<String>> getTranslatedMeaning() {
        return translatedMeaning;
    }

    public TranslatedMeaning(Map<String, List<String>> translatedMeaning) {
        this.translatedMeaning = translatedMeaning;
    }

    public void setTranslatedMeaning(Map<String, List<String>> translatedMeaning) {
        this.translatedMeaning = translatedMeaning;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("\n" + "\uD83D\uDC49" +  "Значения:");
        String prefixSpecification = "\n";
        String prefixMeaning = "\n    ";

        //tried to sort entrySet like that after fetching from database,
        //TreeMap didn't work as desired:(

        for (Map.Entry<String, List<String>> entry: translatedMeaning.entrySet().stream()
                    .sorted(Comparator.comparing(entry -> entry.getValue().stream()
                            .filter(Predicate.not(String::isEmpty))
                            .findFirst()
                            .orElse("")))
                    .collect(Collectors.toList())) {
            result.append(prefixSpecification);
            result.append("\uD83D\uDD39");
            result.append(entry.getKey());
            for (String meaning: entry.getValue().stream()
                    .filter(Predicate.not(String::isEmpty))
                    .sorted()
                    .collect(Collectors.toList())) {
                result.append(prefixMeaning);
                result.append("\u25AA");
                result.append(meaning);
            }
        }
        return result.toString();
    }

}

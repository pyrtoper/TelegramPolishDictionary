package com.pyrtoper.dictionary.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "meanings")
public class TranslatedMeaning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "meanings_json")
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
        for (Map.Entry<String, List<String>> entry: translatedMeaning.entrySet()) {
            result.append(entry.getKey());
            result.append("\n");
            for (String meaning: entry.getValue()) {
                result.append(meaning);
                result.append("\n");
            }
        }
        return result.toString();
    }
}

package com.pyrtoper.dictionary.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "wordforms")
public class WordForms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Type(type = "com.vladmihalcea.hibernate.type.json.JsonType")
    @Column(name = "word_forms_json")
    private Map<String, Map<String, String>> wordForms;

    public WordForms() {
    }

    public WordForms(Map<String, Map<String, String>> wordForms) {
        this.wordForms = wordForms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, Map<String, String>> getWordForms() {
        return wordForms;
    }

    public void setWordForms(Map<String, Map<String, String>> wordForms) {
        this.wordForms = wordForms;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Map<String, String>> entryGeneral: wordForms.entrySet()) {
            result.append(entryGeneral.getKey());
            result.append(":");
            for (Map.Entry<String, String> caseEntry: entryGeneral.getValue().entrySet()) {
                result.append(caseEntry.getKey());
                result.append(": ");
                result.append(caseEntry.getValue());
                result.append("\n");
            }
        }
        return result.toString();
    }
}

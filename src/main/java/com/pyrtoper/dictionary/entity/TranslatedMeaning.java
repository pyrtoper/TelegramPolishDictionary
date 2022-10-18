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
    private Integer id;

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

}

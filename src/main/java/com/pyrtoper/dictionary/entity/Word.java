package com.pyrtoper.dictionary.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "words")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            targetEntity = com.pyrtoper.dictionary.entity.WordForms.class)
    @JoinColumn(name = "word_forms_id")
    private WordForms wordForms;


    @Type(type = "com.vladmihalcea.hibernate.type.json.JsonType")
    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = com.pyrtoper.dictionary.entity.TranslatedMeaning.class)
    @JoinColumn(name = "meaning_id")
    private TranslatedMeaning translatedMeanings;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "translations_words",
            joinColumns = @JoinColumn(name = "word_id"),
            inverseJoinColumns = @JoinColumn(name = "translation_id")
    )
    private Set<Translation> translationSet;

    public Word(String name) {
        this.name = name;
    }

    public Word() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TranslatedMeaning getTranslatedMeanings() {
        return translatedMeanings;
    }

    public void setTranslatedMeanings(TranslatedMeaning translatedMeanings) {
        this.translatedMeanings = translatedMeanings;
    }

    public WordForms getWordForms() {
        return wordForms;
    }

    public void setWordForms(WordForms wordForms) {
        this.wordForms = wordForms;
    }

    public Set<Translation> getTranslationList() {
        return translationSet;
    }

    public void setTranslationList(Set<Translation> translationList) {
        this.translationSet = translationList;
    }

    public void addTranslation(Translation translation) {
        if (translationSet == null) {
            translationSet = new HashSet<>();
        }
        translationSet.add(translation);
    }

    @Override
    public String toString() {
        return "Слово: " + name + '\n' +
                "Значения: " + translatedMeanings.toString() + "\n" +
                "Формы слова: " + wordForms.toString();
    }
}

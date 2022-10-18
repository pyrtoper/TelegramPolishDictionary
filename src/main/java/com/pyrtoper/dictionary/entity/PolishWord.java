package com.pyrtoper.dictionary.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "words")
@NamedEntityGraph(name = "graph.Word.meanings.wordForms.translations",
        attributeNodes = {@NamedAttributeNode("translatedMeanings"),
                @NamedAttributeNode("wordForms"),
                @NamedAttributeNode("translationSet")})
public class PolishWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            targetEntity = com.pyrtoper.dictionary.entity.WordForms.class)
    @JoinColumn(name = "word_forms_id", referencedColumnName = "id")
    private WordForms wordForms;


    @Type(type = "com.vladmihalcea.hibernate.type.json.JsonType")
    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = com.pyrtoper.dictionary.entity.TranslatedMeaning.class)
    @JoinColumn(name = "meaning_id", referencedColumnName = "id")
    private TranslatedMeaning translatedMeanings;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "translation_word",
            joinColumns = @JoinColumn(name = "word_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "translation_id", referencedColumnName = "id")
    )
    private Set<Translation> translationSet = new LinkedHashSet<>();

    public PolishWord(String name) {
        this.name = name;
    }

    public PolishWord() {
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

    public Set<Translation> getTranslationSet() {
        return translationSet;
    }

    public void setTranslationSet(Set<Translation> translationList) {
        this.translationSet = translationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PolishWord)) {
            return false;
        }
        PolishWord other = (PolishWord) o;
        return id != null &&
            id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "PolishWord{" +
            "name='" + name + '\'' +
            '}';
    }
}

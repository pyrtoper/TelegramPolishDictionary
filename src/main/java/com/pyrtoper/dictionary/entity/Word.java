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

    public Set<Translation> getTranslationSet() {
        return translationSet;
    }

    public void setTranslationSet(Set<Translation> translationList) {
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
        StringBuilder result = new StringBuilder();
        result.append("Слово: ");
        result.append(name);
        result.append(translatedMeanings.toString());
        result.append(wordForms.toString());
        if (!translationSet.isEmpty()) {
            result.append("\n\uD83D\uDC49Возможный перевод на русский: ");
            String prefix = "";
            for (Translation translation: translationSet) {
                result.append(prefix);
                result.append(translation.getName());
                prefix = ", ";
            }
        }
        return result.toString();
    }


}

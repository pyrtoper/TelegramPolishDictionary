package com.pyrtoper.dictionary.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "translations")
@NamedEntityGraph(name = "graph.Translation.wordSet",
        attributeNodes = @NamedAttributeNode("wordSet"))
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "translation_word",
            joinColumns = @JoinColumn(name = "translation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "word_id", referencedColumnName = "id")
    )
    private Set<Word> wordSet;

    public Translation() {
    }

    public int getId() {
        return id;
    }

    public Translation(String name) {
        this.name = name;
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

    public Set<Word> getWordSet() {
        return wordSet;
    }

    public void setWordSet(Set<Word> wordList) {
        this.wordSet = wordList;
    }

    public void addOriginalWord(Word word) {
        if (wordSet == null) {
            wordSet = new HashSet<>();
        }
        wordSet.add(word);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (wordSet.isEmpty()) {
            result.append("К сожалению, на польский перевести не удалось :(." +
                    "Мой создатель уже оповещен об этом!");
        } else {
            result.append("\uD83D\uDC49Возможный перевод на польский:\n");
            String prefix = "";
            for (Word word: wordSet) {
                result.append(prefix);
                result.append(word.getName());
                prefix = ", ";
            }
        }
        return result.toString();
    }
}

package com.pyrtoper.dictionary.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "translations")
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    private String text;

    @Column
    private String orderKey;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "translations_words",
            joinColumns = @JoinColumn(name = "translation_id"),
            inverseJoinColumns = @JoinColumn(name = "word_id")
    )
    private List<Word> wordList;

    public Translation() {
    }

    public int getId() {
        return id;
    }

    public Translation(String text) {
        this.text = text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }

    public void addOriginalWord(Word word) {
        if (wordList == null) {
            wordList = new ArrayList<>();
        }
        wordList.add(word);
    }

    @Override
    public String toString() {
        return "Translation{" +
                ", orderKey='" + orderKey + '\'' +
                "text='" + text + '\'' +
                '}';
    }
}

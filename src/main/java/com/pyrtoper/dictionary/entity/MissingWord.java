package com.pyrtoper.dictionary.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "missing_words")
public class MissingWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "word_name")
    private String wordName;
    @Column(name = "date_time_of_receiving")
    private LocalDateTime localDateTime;

    public MissingWord() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "MissingWord: " + wordName + ", localDateTime: " + localDateTime;
    }
}

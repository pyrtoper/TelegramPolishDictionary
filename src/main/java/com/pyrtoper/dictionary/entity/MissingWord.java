package com.pyrtoper.dictionary.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "missing_words")
public class MissingWord{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "word_name")
    private String wordName;
    @Column(name = "date_time_of_receiving")
    private LocalDateTime localDateTime;

    public MissingWord() {
    }

    public MissingWord(String wordName, LocalDateTime localDateTime) {
        this.wordName = wordName;
        this.localDateTime = localDateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MissingWord)) {
            return false;
        }
        MissingWord other = (MissingWord) obj;
        return id != null &&
            id.equals(other.getId());
    }

    @Override
    public String toString() {
        return "MissingWord: " + wordName + ", localDateTime: " + localDateTime;
    }
}

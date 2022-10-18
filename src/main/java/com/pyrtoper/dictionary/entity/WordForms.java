package com.pyrtoper.dictionary.entity;

import com.pyrtoper.dictionary.constant.Declination;
import com.pyrtoper.dictionary.constant.Numeral;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "word_forms")
public class WordForms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Type(type = "com.vladmihalcea.hibernate.type.json.JsonType")
    @Column(name = "word_forms_jsonb")
    private Map<String, Map<Numeral, Map<Declination, String>>> wordForms = new HashMap<>();

    public WordForms() {
    }

    public WordForms(Map<String, Map<Numeral, Map<Declination, String>>> wordForms) {
        this.wordForms = wordForms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, Map<Numeral, Map<Declination, String>>> getWordForms() {
        return wordForms;
    }

    public void setWordForms(Map<String, Map<Numeral, Map<Declination, String>>> wordForms) {
        this.wordForms = wordForms;
    }

}

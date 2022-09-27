package com.pyrtoper.dictionary.entity;

import com.pyrtoper.dictionary.constant.Declination;
import com.pyrtoper.dictionary.constant.Numeral;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "word_forms")
public class WordForms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

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

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("\nФормы слова:\n");
        String prefixGeneral = "";
        String prefixNumeral = "\n    ";
        String prefixDeclination = "\n        ";
        for (Map.Entry<String, Map<Numeral, Map<Declination, String>>> generalEntry: sortedEntrySet(wordForms)) {
            if (generalEntry.getValue().isEmpty()) {
                continue;
            }
            result.append(prefixGeneral);
            result.append(generalEntry.getKey());
            result.append(":");
            prefixGeneral = "\n";
            for (Map.Entry<Numeral, Map<Declination, String>> numeralEntry: sortedEntrySet(generalEntry.getValue())) {
                result.append(prefixNumeral);
                result.append(numeralEntry.getKey().getRussianNumeral());
                result.append(":");
                for (Map.Entry<Declination, String> declinationEntry: sortedEntrySet(numeralEntry.getValue())) {
                    result.append(prefixDeclination);
                    result.append(declinationEntry.getKey().getName());
                    result.append(": ");
                    result.append(declinationEntry.getValue());
                }
            }
        }
        if (result.toString().equals("\nФормы слова:\n")) {
            return "";
        }
        return result.toString();
    }

    private <K extends Comparable<? super K>, V> List<Map.Entry<K, V>> sortedEntrySet(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toList());
    }
}

package com.pyrtoper.dictionary.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "translations")
@NamedEntityGraph(name = "graph.Translation.wordSet",
        attributeNodes = @NamedAttributeNode("polishWordSet"))
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "translation_word",
            joinColumns = @JoinColumn(name = "translation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "word_id", referencedColumnName = "id")
    )
    private Set<PolishWord> polishWordSet;

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

    public Set<PolishWord> getWordSet() {
        return polishWordSet;
    }

    public void setWordSet(Set<PolishWord> polishWordList) {
        this.polishWordSet = polishWordList;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Translation)) {
            return false;
        }
        Translation other = (Translation) obj;
        return id != null &&
            id.equals(other.getId());
    }

    @Override
    public String toString() {
        return "Translation{" +
            "name='" + name + '\'' +
            '}';
    }
}

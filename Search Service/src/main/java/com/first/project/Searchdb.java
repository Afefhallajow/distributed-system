package com.first.project;

import javax.persistence.*;

@Entity
@Table(name = "searchdb")
public class Searchdb {

    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "key")
    private String key;


    @Column(name = "search_number")
    private int search_number;

    @Column(name = "search_text")
    private String search_text;

    public Searchdb() {
    }

    public Searchdb(String key, String search_text, int search_number) {

        this.key = key;
        this.search_text = search_text;
        this.search_number = search_number;
    }

    public int getId() {
        return id;
    }

    public String getSearch_text() {
        return search_text;
    }

    public void setSearch_text(String search_text) {
        this.search_text = search_text;
    }

    public void setId(int id) {
        id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getSearch_number() {
        return search_number;
    }

    public void setSearch_number(int search_number) {
        this.search_number = search_number;
    }
}

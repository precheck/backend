package com.cqprecheck.precheck.Models;

@javax.persistence.Entity
public class Article {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

package com.cqprecheck.precheck.Models;

import javax.persistence.Entity;

@Entity
public class Article {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

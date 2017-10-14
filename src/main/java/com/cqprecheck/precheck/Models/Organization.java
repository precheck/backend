package com.cqprecheck.precheck.Models;

public class Organization {

    private String name;
    private Long id;
    private Account[] accounts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account[] getAccounts() {
        return accounts;
    }

}

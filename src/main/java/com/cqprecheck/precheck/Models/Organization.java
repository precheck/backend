package com.cqprecheck.precheck.Models;

public class Organization {

    private String name;
    private Integer id;
    private Account[] accounts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account[] getAccounts() {
        return accounts;
    }

}

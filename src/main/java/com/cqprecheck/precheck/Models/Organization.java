package com.cqprecheck.precheck.Models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Organization {

    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "organization")
    private List<Account> accounts;

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

    public List<Account> getAccounts() {
        return accounts;
    }

    public Organization(String name) {
        this.name = name;
    }

    public Organization() { }
}

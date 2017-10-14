package com.cqprecheck.precheck.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@javax.persistence.Entity
public class Account {

    private String username;
    @JsonIgnore
    private String password;
    private String email;

    @ManyToOne
    private Organization organization;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Account(){}

    public Account(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Account(String username, String password, Organization organization){
        this(username, password);
        this.organization = organization;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization_id) {
        this.organization = organization;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id;}
}

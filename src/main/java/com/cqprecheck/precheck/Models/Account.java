package com.cqprecheck.precheck.Models;

import javax.persistence.Entity;

@Entity
public class Account {

    private String username;
    private String password;
    private String email;
    private Organization organization_id;
    private Integer id;

    private Account(){}

    private Account(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
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

    public Organization getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(Organization organization_id) {
        this.organization_id = organization_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

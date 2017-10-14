package com.cqprecheck.precheck.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.language.v1beta2.EntityMention;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
public class Entity {

    private String name;

    @ManyToOne
    @JsonIgnore
    private Organization organization;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String url;
    private String type;



    @Transient
    public List<Integer> locations;

    public Entity(com.google.cloud.language.v1beta2.Entity entity){
        this.name = entity.getName();
        this.type = entity.getType().toString();

        this.locations = new ArrayList<>();
        for(EntityMention mention: entity.getMentionsList()){
            this.locations.add(mention.getText().getBeginOffset());
        }

    }

    public Entity() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Integer> getLocations() {
        return locations;
    }
}

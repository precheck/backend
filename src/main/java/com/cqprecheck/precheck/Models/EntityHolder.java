package com.cqprecheck.precheck.Models;

import java.util.List;

public class EntityHolder {
    private List<Entity> entities;
    private List<Integer> locations;

    public EntityHolder(List<Entity> entities, List<Integer> locations) {
        this.entities = entities;
        this.locations = locations;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public List<Integer> getLocations() {
        return locations;
    }

    public void setLocations(List<Integer> locations) {
        this.locations = locations;
    }
}

package com.applications.asm.places.model;

public class CriterionVM {
    public enum Type {
        SORT, PRICE
    }

    private String id;
    private String name;

    public CriterionVM(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

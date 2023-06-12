package com.kaab.entity;

public enum ActivationStatus {
    DEACTIVE("DEACTIVE"),
    ACTIVE("ACTIVE");

    private String name;

    ActivationStatus(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}

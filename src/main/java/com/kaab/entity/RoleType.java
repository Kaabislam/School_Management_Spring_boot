package com.kaab.entity;

public enum RoleType {
    ADMIN("Admin"),
    TEACHER("Teacher"),
    STUDENT("Student");

    private String name;

    RoleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

package com.lwl.entity;

public class BookCase {
    private Integer id;
    private String name;

    public BookCase(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public BookCase() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

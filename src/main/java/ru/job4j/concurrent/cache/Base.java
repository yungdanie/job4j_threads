package ru.job4j.concurrent.cache;

public class Base {
    private final int id;
    private final int version;
    private String name;

    public Base(String name, int id, int version) {
        this.name = name;
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
package ru.job4j.concurrent;

public final class Node<T extends Cloneable> {
    private final Node<T> next;
    private final T value;

    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public Object getValue() {
        return value;
    }
}

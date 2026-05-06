package com.nikhil.utils;

public class DoublyLinkedListNode<K, V> {
    private K key;
    private V value;
    public DoublyLinkedListNode<K, V> prev, next;

    public DoublyLinkedListNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.prev = null;
        this.next = null;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}

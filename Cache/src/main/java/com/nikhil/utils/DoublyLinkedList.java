package com.nikhil.utils;

public class DoublyLinkedList<K, V> {
    private DoublyLinkedListNode<K, V> head;
    private DoublyLinkedListNode<K, V> tail;

    public DoublyLinkedList() {
        head = null;
        tail = null;
    }

    public void addToStart(DoublyLinkedListNode<K, V> node) {
        if (head == null) {
            head = node;
            tail = node;
            return;
        }
        node.next = head;
        head.prev = node;
        head = node;
    }

    public DoublyLinkedListNode<K,V> removeFromEnd() {
        if (head == null) {
            return null;
        }
        DoublyLinkedListNode<K,V> node = tail;
        if (tail.prev != null) {
            tail = tail.prev;
            tail.next = null;
        } else {
            head = null;
            tail = null;
        }
        return node;
    }

    public void removeNode(DoublyLinkedListNode<K, V> node) {
        if (head == null || node == null) {
            return;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        node.prev = null;
        node.next = null;
    }
}
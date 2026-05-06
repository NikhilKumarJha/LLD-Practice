package com.nikhil.EvictionAlgorithm;

import com.nikhil.utils.DoublyLinkedList;
import com.nikhil.utils.DoublyLinkedListNode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class LRUEvictionAlgorithm<K, V> implements EvictionAlgorithm<K, V> {

    private DoublyLinkedList<K, V> list;
    private Map<K, DoublyLinkedListNode<K, V>> map;
    private final int capacity;
    private final Map<K, ReentrantLock> keyLock;
    private final ReentrantLock listLock;

    public LRUEvictionAlgorithm(int capacity) {
        list = new DoublyLinkedList<>();
        map = new ConcurrentHashMap<>();
        this.capacity = capacity;
        keyLock = new ConcurrentHashMap<>();
        listLock = new ReentrantLock();
    }

    private ReentrantLock getLock(K key) {
        return keyLock.computeIfAbsent(key, k -> new ReentrantLock());
    }

    @Override
    public V getKey(K key) {
        ReentrantLock lock = getLock(key);
        lock.lock();
        try {
            listLock.lock();
            try {
                DoublyLinkedListNode<K, V> node = map.get(key);
                if (node == null) {
                    return null;
                }
                list.removeNode(node);
                list.addToStart(node);
                return node.getValue();
            } finally {
                listLock.unlock();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void putKey(K key, V value) {
        ReentrantLock lock = getLock(key);
        lock.lock();
        try {
            listLock.lock();
            try {
                DoublyLinkedListNode<K, V> node = map.get(key);
                if (node == null) {
                    DoublyLinkedListNode<K, V> currNode = new DoublyLinkedListNode<>(key, value);
                    list.addToStart(currNode);
                    map.put(key, currNode);
                    if (map.size() > capacity) {
                        DoublyLinkedListNode<K, V> removedNode = list.removeFromEnd();
                        map.remove(removedNode.getKey());
                    }
                } else {
                    node.setValue(value);
                    list.removeNode(node);
                    list.addToStart(node);
                }
            } finally {
                listLock.unlock();
            }
        } finally {
            lock.unlock();
        }
    }

}
package com.nikhil.EvictionAlgorithm;

public interface EvictionAlgorithm<K, V> {
    V getKey(K key);

    void putKey(K key, V value);

}

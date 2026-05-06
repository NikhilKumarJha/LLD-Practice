package com.nikhil;

import com.nikhil.EvictionAlgorithm.EvictionAlgorithm;
import com.nikhil.EvictionAlgorithm.LRUEvictionAlgorithm;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        EvictionAlgorithm<String, Integer> cache = new LRUEvictionAlgorithm<>(3);

        cache.putKey("a",1);
        cache.putKey("b",2);
        cache.putKey("c",3);

        System.out.println(cache.getKey("a"));

        cache.putKey("d", 4);
        System.out.println(cache.getKey("b"));
    }
}

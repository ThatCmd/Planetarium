/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.console.output.helper;

/**
 *
 * @author TTT
 * @param <K>
 * @param <V>
 */
public class BasicPair<K, V> {

    private final K key;
    private final V value;

    public BasicPair(K k, V v) {
        key = k;
        value = v;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

}

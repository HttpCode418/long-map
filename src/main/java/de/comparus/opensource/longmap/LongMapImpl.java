package de.comparus.opensource.longmap;

import java.util.*;
import java.util.stream.Stream;

public class LongMapImpl<V> implements LongMap<V> {

    private static final int TABLE_SIZE = 4096;

    private LinkedList<Pair>[] table = new LinkedList[TABLE_SIZE];

    private long size;

    public V put(long key, V value) {

        if (table.length == Integer.MAX_VALUE) {
            throw new IllegalStateException();
        }

        List<Pair> pairs = getPairs(key);
        Pair pair = pairs.stream().filter(vPair -> key == vPair.getKey()).findFirst().orElse(null);

        if (pair == null) {
            getPairs(key).add(new Pair(key, value));
            size++;
        } else pair.setValue(value);

        return value;

    }

    public V get(long key) {
        return getPairs(key).stream()
                .filter(vPair -> key == vPair.getKey())
                .map(Pair::getValue).findFirst()
                .orElse(null);
    }

    public V remove(long key) {
        LinkedList<Pair> pairs = getPairs(key);

        if (pairs.isEmpty()) {
            return null;
        }

        Pair pair = pairs.stream()
                .filter(vPair -> key == vPair.getKey())
                .findFirst()
                .orElse(null);

        if (pair == null) {
            return null;
        }
        if (pairs.removeIf(p -> p.getKey() == key)) {
            size--;
        }
        return pair.getValue();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(long key) {
        if (isEmpty()) {
            return false;
        }

        LinkedList<Pair> pairs = getPairs(key);
        if (pairs.isEmpty()) {
            return false;
        }

        return pairs.stream()
                .anyMatch(vPair -> vPair.getKey() == key);
    }

    public boolean containsValue(V value) {
        if (isEmpty()) {
            return false;
        }
        return Arrays.asList(values()).contains(value);
    }

    public long[] keys() {
        if (isEmpty()) {
            return new long[0];
        }

        return Arrays.stream(table)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .mapToLong(Pair::getKey)
                .toArray()
                ;

    }

    public Object[] values() {
        if (isEmpty()) {
            return new Object[0];
        }
        return valuesStream().toArray();
    }

    // variant of generic array returning
    public V[] values(V[] arr) {
        if (isEmpty()) {
            return arr;
        }
        return valuesStream().toArray(s -> Arrays.copyOf(arr, s));
    }

    public long size() {
        return this.size;
    }

    public void clear() {
        table = new LinkedList[TABLE_SIZE];
        size = 0;

    }

    private int hash(Long key) {
        int hash = (int) (key % TABLE_SIZE);
        return hash < 0 ? -1 * hash : hash;
    }

    private LinkedList<Pair> getPairs(long key) {
        LinkedList<Pair> linkedList = table[hash(key)];
        if (linkedList == null) {
            linkedList = new LinkedList<>();
            table[hash(key)] = linkedList;
        }
        return linkedList;
    }

    private Stream<V> valuesStream() {
        return Arrays.stream(table)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .map(p -> p.value);
    }

    class Pair {

        private final long key;

        private V value;

        public long getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        Pair(long key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}

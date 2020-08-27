package de.comparus.opensource.longmap;

public interface LongMap<V> {
    V put(long key, V value);
    V get(long key);
    V remove(long key);

    boolean isEmpty();
    boolean containsKey(long key);
    boolean containsValue(V value);

    long[] keys();
    Object[] values();

    /* The language specification does not support generic array creation
       (for details see https://stackoverflow.com/questions/372250/generics-arrays-and-the-classcastexception)
    V[] values();*/

    long size();
    void clear();
}

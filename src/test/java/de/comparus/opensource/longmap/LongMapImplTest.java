package de.comparus.opensource.longmap;

import org.junit.Assert;
import org.junit.Test;

public class LongMapImplTest {

    @Test
    public void testCrudOperations() {
        LongMap<Integer> intMap = new LongMapImpl<>();

        intMap.put(12, 101);
        Assert.assertEquals(101, (int) intMap.get(12));

        intMap.put(12, 111);
        Assert.assertEquals(111, (int) intMap.get(12));

        intMap.remove(12);
        Assert.assertNull(intMap.get(12));
    }


    @Test
    public void testStatusChecking() {
        LongMap<Integer> intMap = new LongMapImpl<>();
        Assert.assertTrue(intMap.isEmpty());

        intMap.put(10, 2);

        Assert.assertTrue(intMap.containsKey(10));
        Assert.assertTrue(intMap.containsValue(2));

        Assert.assertFalse(intMap.containsKey(2));
        Assert.assertFalse(intMap.containsValue(10));

    }

    @Test
    public void testGettingAllKeysAndValues() {
        LongMap<Integer> intMap = new LongMapImpl<>();

        intMap.put(0, 1);
        intMap.put(10, 2);
        intMap.put(100, 3);

        long[] expectedKeys = {0, 10, 100};
        Object[] expectedValues = {1, 2, 3};

        Assert.assertArrayEquals(expectedKeys, intMap.keys());
        Assert.assertArrayEquals(expectedValues, intMap.values());
    }

    @Test
    public void testClear() {
        LongMap<Integer> integerLongMap = new LongMapImpl<>();

        integerLongMap.put(0, 1);
        integerLongMap.put(10, 2);

        integerLongMap.clear();

        Assert.assertFalse(integerLongMap.containsKey(0));
        Assert.assertFalse(integerLongMap.containsKey(10));
        Assert.assertTrue(integerLongMap.isEmpty());
    }

}

package com.tvd12.dahlia.core.test.tree;

import com.tvd12.dahlia.core.btree.BTree;
import com.tvd12.dahlia.core.tree.Tree;
import com.tvd12.dahlia.core.tree.TreeWalker;
import com.tvd12.dahlia.math.Operation;
import com.tvd12.ezyfox.util.EzyMapBuilder;
import lombok.AllArgsConstructor;
import org.testng.annotations.Test;

import java.util.*;
import java.util.Map.Entry;

import static org.testng.Assert.assertEquals;

public class TreeTest {

    @Test
    public static void isEmptyTest() {
        FlatTree tree = new FlatTree();
        //noinspection ConstantConditions
        assert tree.isEmpty();
        tree.put(1, 1);
        //noinspection ConstantConditions
        assert !tree.isEmpty();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        Tree<Integer, Integer> tree = new BTree<>(Comparator.comparing(i -> i));
        Set<Integer> keySet = new HashSet<>();
        List<Integer> valueList = new ArrayList<>();
        for (int i = 1; i <= 100; ++i) {
            tree.put(i, i);
            keySet.add(i);
            valueList.add(i);
        }
        tree.putAll(EzyMapBuilder.mapBuilder()
            .put(101, 101)
            .build());
        System.out.println(tree);
        keySet.add(101);
        valueList.add(101);
        assert tree.get(99).equals(99);
        assert tree.remove(50).equals(50);
        keySet.remove(50);
        valueList.remove((Object) 50);
        assertEquals(tree.keySet(), keySet);
        assertEquals(tree.values(), valueList);
        assert tree.entrySet().size() == 100;
        //noinspection ConstantConditions
        assert tree.size() == 100;
        assert tree.containsKey(98);
        assert !tree.containsKey(50);
        assert tree.containsValue(98);
        assert !tree.containsValue(50);
        assert tree.get(50) == null;
        Entry<Integer, Integer> entry = tree.search(101);
        entry.setValue(102);
        assert tree.get(101).equals(102);
        tree.put(102, null);
        tree.put(103, null);
        tree.put(104, 104);
        assert tree.containsValue(null);
    }

    @Test
    public void containsValueTest() {
        Tree<Integer, A> tree = new BTree<>(10);
        tree.put(1, new A(1));
        tree.put(2, null);
        tree.put(3, new A(3));
        assert tree.containsValue(new A(1));
        assert tree.containsValue(new A(3));
        assert tree.containsValue(null);
        assert !tree.containsValue(new A(4));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void compareKeyNoComparableTest() {
        //noinspection MismatchedQueryAndUpdateOfCollection
        Tree<Object, Object> tree = new BTree<>(10);
        tree.put(new Object(), new Object());
        tree.put(new Object(), new Object());
    }

    @AllArgsConstructor
    public static class A implements Comparable<A> {

        private final int value;

        @Override
        public int compareTo(A o) {
            return value - o.value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            return value == ((A) obj).value;
        }
    }

    public static class FlatTree extends Tree<Integer, Integer> {

        private final Map<Integer, Integer> map = new HashMap<>();

        @Override
        public void clear() {
        }

        @Override
        public void insert(Integer key, Integer value) {
            map.put(key, value);
        }

        @Override
        public Integer delete(Integer key) {
            return null;
        }

        @Override
        public Entry<Integer, Integer> search(Integer key) {
            return null;
        }

        @Override
        public void walk(TreeWalker<Integer, Integer> walker) {
            for (Integer key : map.keySet()) {
                Integer value = map.get(key);
                if (walker.next()) {
                    walker.accept(new Tree.Entry<>(key, value));
                }
            }
        }

        @Override
        public void walkReverse(TreeWalker<Integer, Integer> walker) {
        }

        @Override
        public Entry<Integer, Integer> search(Integer key, Operation op) {
            return null;
        }
    }
}

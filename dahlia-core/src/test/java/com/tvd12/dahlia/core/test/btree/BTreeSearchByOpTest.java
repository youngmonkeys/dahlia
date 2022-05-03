package com.tvd12.dahlia.core.test.btree;

import com.tvd12.dahlia.core.btree.BTree;
import com.tvd12.dahlia.math.Operation;
import org.testng.annotations.Test;

public class BTreeSearchByOpTest {

    @Test
    public void searchByOpTest() {
        BTree<Integer, Integer> tree = new BTree<>();
        assert tree.search(1, Operation.EQ) == null;
        for (int i = 1; i <= 100; ++i) {
            tree.insert(i, i);
        }

        System.out.println(tree);

        assert tree.search(10, Operation.EQ).getValue() == 10;
        assert tree.search(-1, Operation.EQ) == null;
        assert tree.search(10, Operation.NEQ).getValue() == 1;
        assert tree.search(0, Operation.NEQ).getValue() == 1;
        assert tree.search(-1, Operation.NEQ).getValue() == 1;
        assert tree.search(100, Operation.NEQ).getValue() == 1;
        assert tree.search(101, Operation.NEQ).getValue() == 1;
        assert tree.search(1, Operation.NEQ).getValue() == 2;
        assert tree.search(10, Operation.GT).getValue() == 11;
        assert tree.search(10, Operation.GTE).getValue() == 10;
        assert tree.search(101, Operation.GTE) == null;
        assert tree.search(10, Operation.LT).getValue() == 9;
        assert tree.search(0, Operation.LT) == null;
        assert tree.search(-1, Operation.LTE) == null;
        assert tree.search(32, Operation.GT).getValue() == 33;
        assert tree.search(100, Operation.GT) == null;

        for (int i = 1; i <= 100; ++i) {
            System.out.println("i = " + i + " : " +
                "(eq)" + tree.search(i, Operation.EQ) + ", " +
                "(gt)" + tree.search(i, Operation.GT) + ", " +
                "(gte)" + tree.search(i, Operation.GTE) + ", " +
                "(lt)" + tree.search(i, Operation.LT) + ", " +
                "(lte)" + tree.search(i, Operation.LTE));
        }

        tree.insert(-1, -1);
        tree.insert(102, 102);

        assert tree.search(0, Operation.EQ) == null;
        assert tree.search(0, Operation.LT).getValue() == -1;
        assert tree.search(0, Operation.LTE).getValue() == -1;

        assert tree.search(101, Operation.EQ) == null;
        assert tree.search(101, Operation.GT).getValue() == 102;
        assert tree.search(101, Operation.GTE).getValue() == 102;

        tree = new BTree<>();
        for (int i = 1; i <= 100; ++i) {
            tree.insert(i % 10, i % 10);
        }

        for (int i = 1; i <= 100; ++i) {
            System.out.println("i = " + i + " : " +
                "(eq)" + tree.search(i, Operation.EQ) + ", " +
                "(gt)" + tree.search(i, Operation.GT) + ", " +
                "(gte)" + tree.search(i, Operation.GTE) + ", " +
                "(lt)" + tree.search(i, Operation.LT) + ", " +
                "(lte)" + tree.search(i, Operation.LTE));
        }

        BTree<Double, Double> dTree = new BTree<>();
        dTree.put(1.1D, 1.1D);
        dTree.put(0.1D, 0.1D);
        dTree.put(1.5D, 1.5D);
        dTree.put(1.6D, 1.6D);
        dTree.put(2.1D, 2.1D);

        assert dTree.search(1.4D, Operation.EQ) == null;
        assert dTree.search(1.1D, Operation.LT).getValue() == 0.1D;
        assert dTree.search(1.1D, Operation.LTE).getValue() == 1.1D;
        assert dTree.search(1.6D, Operation.LT).getValue() == 1.5D;
        assert dTree.search(1.6D, Operation.LTE).getValue() == 1.6D;
        assert dTree.search(2.0D, Operation.LT).getValue() == 1.6D;
        assert dTree.search(2.0D, Operation.LTE).getValue() == 1.6D;
        assert dTree.search(2.0D, Operation.GT).getValue() == 2.1D;
        assert dTree.search(2.0D, Operation.GTE).getValue() == 2.1D;
        assert dTree.search(2.1D, Operation.GTE).getValue() == 2.1D;
        assert dTree.search(2.1D, Operation.GT) == null;
    }
}
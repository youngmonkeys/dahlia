package com.tvd12.dahlia.core.test.btree;

import com.tvd12.dahlia.core.btree.BTree;
import com.tvd12.dahlia.core.btree.BTreeProxy;
import org.testng.annotations.Test;

public class BTreeProxyTest {

    @Test
    public void test() {
        BTree<Integer, Integer> tree = new BTree<>();
        tree.insert(1, 1);
        BTreeProxy<Integer, Integer> proxy = new BTreeProxy<>(tree);
        assert proxy.getMinEntry() == 1;
        assert proxy.getMaxDegree() == 3;
        assert proxy.getSplitIndex() == 1;
        BTreeProxy.NodeProxy<Integer, Integer> node = proxy.getRoot();
        assert node.getEntry(2) == null;
    }
}

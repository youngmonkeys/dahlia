package com.tvd12.dahlia.core.test.btree;

import org.testng.annotations.Test;

import com.tvd12.dahlia.core.btree.BTree;
import com.tvd12.dahlia.core.btree.BTreeProxy;

public class BTreeProxyTest {

	@Test
	public void test() {
		BTree tree = new BTree();
		tree.insert(1);
		BTreeProxy proxy = new BTreeProxy(tree);
		assert proxy.getMinEntry() == 1;
		assert proxy.getMaxDegree() == 3;
		assert proxy.getSplitIndex() == 1;
		BTreeProxy.NodeProxy node = proxy.getRoot();
		assert node.getEntry(2) == null;
	}
	
}

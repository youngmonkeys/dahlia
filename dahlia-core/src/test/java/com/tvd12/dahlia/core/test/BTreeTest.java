package com.tvd12.dahlia.core.test;

import org.testng.annotations.Test;

import com.tvd12.dahlia.core.btree.BTree;

public class BTreeTest {

	@Test
	public void test1() {
		BTree tree = new BTree(2);
		tree.insert(1);
		tree.insert(2);
		tree.insert(3);
		tree.insert(4);
		tree.insert(5);
		tree.insert(6);
		tree.insert(7);
		BTreePrinter printer = new BTreePrinter();
		tree.accept(printer);
		System.out.append(printer.print());
	}
	
}

package com.tvd12.dahlia.core.test.btree;

import org.testng.annotations.Test;

import com.tvd12.dahlia.core.btree.BTree;
import com.tvd12.dahlia.core.btree.BTreePrinter;

public class BTreeStealFromRightTest {

	@Test
	public void normalCase() {
		BTree<Integer, Integer> tree = new BTree<>(3);
		for(int i = 1 ; i <= 25 ; ++i)
			tree.insert(i, i);
		BTreePrinter printer = new BTreePrinter();
		tree.accept(printer);
		System.out.append(printer.print());
		tree.delete(3);
		tree.delete(6);
		tree.accept(printer);
		System.out.append(printer.print());
	}
	
	
	@Test
	public void nodeLeafCase() {
		BTree<Integer, Integer> tree = new BTree<>(3);
		for(int i = 1 ; i <= 25 ; ++ i)
			tree.insert(i, i);
		BTreePrinter printer = new BTreePrinter();
		tree.accept(printer);
		System.out.append(printer.print());
		tree.delete(20);
		tree.accept(printer);
		System.out.append(printer.print());
	}
}

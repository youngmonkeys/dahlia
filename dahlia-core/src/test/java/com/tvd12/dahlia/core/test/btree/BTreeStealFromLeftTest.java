package com.tvd12.dahlia.core.test.btree;

import org.testng.annotations.Test;

import com.tvd12.dahlia.core.btree.BTree;
import com.tvd12.dahlia.core.btree.BTreePrinter;

public class BTreeStealFromLeftTest {

	@Test
	public void normalCaseTest() {
		BTree<Integer, Integer> tree = new BTree<>(3);
		for(int i = 1 ; i <= 25 ; ++i)
			tree.insert(i, i);
		BTreePrinter printer = new BTreePrinter();
		tree.accept(printer);
		System.out.append(printer.print());
		tree.delete(12);
		tree.delete(15);
		tree.delete(18);
		tree.accept(printer);
		System.out.append(printer.print());
	}
	
	@Test
	public void leftSibNotLeafCaseTest() {
		BTree<Integer, Integer> tree = new BTree<>(3);
		for(int i = 1 ; i <= 37 ; ++i)
			tree.insert(i, i);
		
		BTreePrinter printer = new BTreePrinter();
		tree.accept(printer);
		System.out.append(printer.print());
		tree.delete(21);
		tree.delete(24);
		tree.delete(30);
		tree.delete(34);
		tree.accept(printer);
		System.out.append(printer.print());
	}
	
}

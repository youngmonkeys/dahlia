package com.tvd12.dahlia.core.test.btree;

import org.testng.annotations.Test;

import com.tvd12.dahlia.core.btree.BTree;
import com.tvd12.dahlia.core.btree.BTreePrinter;

public class BTreeDegree7Test {

	@Test
	public void test() {
		BTree<Integer, Integer> tree = new BTree<>(4);
		for(int i = 1 ; i <= 100 ; ++ i)
			tree.insert(i, i);
		BTreePrinter printer = new BTreePrinter();
		tree.accept(printer);
		System.out.append(printer.print());
		tree.delete(20);
		tree.delete(21);
		tree.delete(22);
		tree.accept(printer);
		System.out.append(printer.print());
		
		System.out.println("search result: " + tree.search(30));
		System.out.println("search result: " + tree.search(20));
	}
	
	@Test
	public void duplicateKeyTest() {
		BTree<Integer, Integer> tree = new BTree<>(3);
		for(int i = 1 ; i <= 100 ; ++ i)
			tree.insert(i % 15, i % 15);
		BTreePrinter printer = new BTreePrinter();
		tree.accept(printer);
		System.out.append(printer.print());
		tree.delete(1);
		tree.delete(2);
		tree.delete(2);
		tree.delete(2);
		tree.delete(3);
		tree.accept(printer);
		System.out.append(printer.print());
	}
	
}

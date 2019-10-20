package com.tvd12.dahlia.core.test.btree;

import org.testng.annotations.Test;

import com.tvd12.dahlia.core.btree.BTree;
import com.tvd12.dahlia.core.btree.BTreePrinter;
import com.tvd12.test.reflect.FieldUtil;
import com.tvd12.test.reflect.MethodInvoker;

public class BTreeTest {

	@Test
	public void normalCaseTest() {
		BTree<Integer, Integer> tree = new BTree<>(2);
		for(int i = 1 ; i <= 17 ; ++i)
			tree.insert(i, i);
		BTreePrinter printer = new BTreePrinter();
		tree.accept(printer);
		System.out.append(printer.print());
		tree.delete(2);
		tree.accept(printer);
		System.out.append(printer.print());
	}
	
	@Test
	public void deleteAllCaseTest() {
		BTree<Integer, Integer> tree = new BTree<>(2);
		tree.insert(9, 9);
		BTreePrinter printer = new BTreePrinter();
		tree.accept(printer);
		System.out.append(printer.print());
		tree.delete(9);
		tree.accept(printer);
		System.out.append(printer.print());
		tree.insert(1, 1);
		tree.insert(2, 2);
		tree.insert(3, 3);
		tree.accept(printer);
		System.out.append(printer.print());
		tree.delete(3);
		tree.delete(2);
		tree.delete(1);
		tree.accept(printer);
		System.out.append(printer.print());
	}
	
	@Test
	public void deleteInRootCaseTest() {
		BTree<Integer, Integer> tree = new BTree<>(3);
		tree.insert(1, 1);
		tree.insert(2, 2);
		tree.insert(3, 3);
		tree.insert(4, 4);
		BTreePrinter printer = new BTreePrinter();
		tree.accept(printer);
		System.out.append(printer.print());
		tree.delete(4);
		tree.delete(3);
		tree.delete(2);
		tree.accept(printer);
		System.out.append(printer.print());
	}
	
	@Test
	public void redeleteCaseTest() {
		BTree<Integer, Integer> tree = new BTree<>(2);
		tree.insert(8, 8);
		BTreePrinter printer = new BTreePrinter();
		tree.accept(printer);
		System.out.append(printer.print());
		tree.delete(2);
		tree.delete(8);
		tree.delete(8);
		tree.accept(printer);
		System.out.append(printer.print());
	}
	
	@Test
	public void deleteNotFoundCaseTest() {
		BTree<Integer, Integer> tree = new BTree<>(2);
		tree.insert(9, 9);
		tree.insert(10, 10);
		tree.insert(11, 11);
		tree.insert(12, 12);
		tree.insert(13, 13);
		tree.insert(14, 14);
		tree.insert(15, 15);
		tree.insert(16, 16);
		tree.insert(17, 17);
		tree.insert(1, 1);
		tree.insert(2, 2);
		tree.insert(3, 3);
		tree.insert(4, 4);
		tree.insert(5, 5);
		tree.insert(6, 6);
		tree.insert(7, 7);
		tree.insert(8, 8);
		BTreePrinter printer = new BTreePrinter();
		tree.accept(printer);
		System.out.append(printer.print());
		tree.delete(100);
		tree.accept(printer);
		System.out.append(printer.print());
	}
	
	@Test
	public void deleteOneIn2DegreeCaseTest() {
		BTree<Integer, Integer> tree = new BTree<>(2);
		tree.insert(9, 9);
		tree.insert(10, 10);
		tree.insert(11, 11);
		tree.insert(12, 12);
		tree.insert(13, 13);
		tree.insert(14, 14);
		tree.insert(15, 15);
		tree.insert(16, 16);
		tree.insert(17, 17);
		tree.insert(1, 1);
		tree.insert(2, 2);
		tree.insert(3, 3);
		tree.insert(4, 4);
		tree.insert(5, 5);
		tree.insert(6, 6);
		tree.insert(7, 7);
		tree.insert(8, 8);
		BTreePrinter printer = new BTreePrinter();
		tree.accept(printer);
		System.out.append(printer.print());
		tree.delete(2);
		tree.accept(printer);
		System.out.append(printer.print());
		System.out.println(tree);
	}
	
	@Test
	public void getMaxLeafNodeCaseTest() {
		BTree<Integer, Integer> tree = new BTree<>();
		for(int i = 1 ; i <= 8 ; ++ i)
			tree.insert(i, i);
		BTreePrinter printer = new BTreePrinter();
		tree.accept(printer);
		System.out.append(printer.print());
		tree.delete(4);
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public void indexOfNodeInParentMagicCaseTest() {
		BTree<Integer, Integer> tree = new BTree<>();
		BTree.Node<Integer, Integer> node = new BTree.Node<>(1);
		System.out.println(node);
		BTree.Node<Integer, Integer> parent = new BTree.Node<>(2);
		FieldUtil.setFieldValue(parent, "entryCount", 2);
		FieldUtil.setFieldValue(node, "parent", parent);
		MethodInvoker.create()
			.object(tree)
			.param(node)
			.method("indexOfNodeInParent")
			.invoke();
	}
	
	@Test
	public void searchTest() {
		BTree<Integer, Integer> tree = new BTree<>();
		assert tree.search(1) == null;
		tree.insert(1, 1);
		assert tree.search(1) != null;
		assert tree.search(2) == null;
	}
}

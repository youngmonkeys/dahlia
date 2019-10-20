package com.tvd12.dahlia.core.test.btree;

import org.testng.annotations.Test;

import com.tvd12.dahlia.core.btree.BTree;
import com.tvd12.dahlia.core.btree.BTreePrinter;
import com.tvd12.test.reflect.FieldUtil;
import com.tvd12.test.reflect.MethodInvoker;

public class BTreeTest {

	@Test
	public void normalCaseTest() {
		BTree tree = new BTree(2);
		tree.insert(1);
		tree.insert(2);
		tree.insert(3);
		tree.insert(4);
		tree.insert(5);
		tree.insert(6);
		tree.insert(7);
		tree.insert(8);
		tree.insert(9);
		tree.insert(10);
		tree.insert(11);
		tree.insert(12);
		tree.insert(13);
		tree.insert(14);
		tree.insert(15);
		tree.insert(16);
		tree.insert(17);
		BTreePrinter printer = new BTreePrinter();
		tree.accept(printer);
		System.out.append(printer.print());
		tree.delete(2);
		tree.accept(printer);
		System.out.append(printer.print());
	}
	
	@Test
	public void deleteAllCaseTest() {
		BTree tree = new BTree(2);
		tree.insert(9);
		BTreePrinter printer = new BTreePrinter();
		tree.accept(printer);
		System.out.append(printer.print());
		tree.delete(9);
		tree.accept(printer);
		System.out.append(printer.print());
		tree.insert(1);
		tree.insert(2);
		tree.insert(3);
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
		BTree tree = new BTree(3);
		tree.insert(1);
		tree.insert(2);
		tree.insert(3);
		tree.insert(4);
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
		BTree tree = new BTree(2);
		tree.insert(8);
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
		BTree tree = new BTree(2);
		tree.insert(9);
		tree.insert(10);
		tree.insert(11);
		tree.insert(12);
		tree.insert(13);
		tree.insert(14);
		tree.insert(15);
		tree.insert(16);
		tree.insert(17);
		tree.insert(1);
		tree.insert(2);
		tree.insert(3);
		tree.insert(4);
		tree.insert(5);
		tree.insert(6);
		tree.insert(7);
		tree.insert(8);
		BTreePrinter printer = new BTreePrinter();
		tree.accept(printer);
		System.out.append(printer.print());
		tree.delete(100);
		tree.accept(printer);
		System.out.append(printer.print());
	}
	
	@Test
	public void deleteOneIn2DegreeCaseTest() {
		BTree tree = new BTree(2);
		tree.insert(9);
		tree.insert(10);
		tree.insert(11);
		tree.insert(12);
		tree.insert(13);
		tree.insert(14);
		tree.insert(15);
		tree.insert(16);
		tree.insert(17);
		tree.insert(1);
		tree.insert(2);
		tree.insert(3);
		tree.insert(4);
		tree.insert(5);
		tree.insert(6);
		tree.insert(7);
		tree.insert(8);
		BTreePrinter printer = new BTreePrinter();
		tree.accept(printer);
		System.out.append(printer.print());
		tree.delete(2);
		tree.accept(printer);
		System.out.append(printer.print());
	}
	
	@Test
	public void getMaxLeafNodeCaseTest() {
		BTree tree = new BTree();
		tree.insert(1);
		tree.insert(2);
		tree.insert(3);
		tree.insert(4);
		tree.insert(5);
		tree.insert(6);
		tree.insert(7);
		tree.insert(8);
		BTreePrinter printer = new BTreePrinter();
		tree.accept(printer);
		System.out.append(printer.print());
		tree.delete(4);
	}
	
	@Test(expectedExceptions = IllegalStateException.class)
	public void indexOfNodeInParentMagicCaseTest() {
		BTree tree = new BTree();
		BTree.Node node = new BTree.Node(1);
		BTree.Node parent = new BTree.Node(2);
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
		BTree tree = new BTree();
		assert tree.search(1) == null;
		tree.insert(1);
		assert tree.search(1) != null;
		assert tree.search(2) == null;
	}
}

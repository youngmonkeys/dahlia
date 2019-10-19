package com.tvd12.dahlia.core.test.btree;

import org.testng.annotations.Test;

import com.tvd12.dahlia.core.btree.BTree;
import com.tvd12.dahlia.core.btree.BTreePrinter;

public class BTreeStealFromLeftTest {

	@Test
	public void normalCaseTest() {
		BTree tree = new BTree(3);
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
		tree.insert(18);
		tree.insert(19);
		tree.insert(20);
		tree.insert(21);
		tree.insert(22);
		tree.insert(23);
		tree.insert(24);
		tree.insert(25);
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
		BTree tree = new BTree(3);
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
		tree.insert(18);
		tree.insert(19);
		tree.insert(20);
		tree.insert(21);
		tree.insert(22);
		tree.insert(23);
		tree.insert(24);
		tree.insert(25);
		tree.insert(26);
		tree.insert(27);
		tree.insert(28);
		tree.insert(29);
		tree.insert(30);
		tree.insert(31);
		tree.insert(32);
		tree.insert(33);
		tree.insert(34);
		tree.insert(35);
		tree.insert(36);
		tree.insert(37);
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

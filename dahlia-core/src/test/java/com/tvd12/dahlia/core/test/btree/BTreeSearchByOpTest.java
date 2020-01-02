package com.tvd12.dahlia.core.test.btree;

public class BTreeSearchByOpTest {

//	@Test
//	public void searchByOpTest() {
//		BTree<Integer, Integer> tree = new BTree<>();
//		assert tree.search(1, Operation.EQ) == null;
//		for(int i = 1 ; i <= 100 ; ++ i)
//			tree.insert(i, i);
//		
//		System.out.println(tree);
//		
//		assert tree.search(10, Operation.EQ).getValue() == 10;
//		assert tree.search(-1, Operation.EQ) == null;
//		assert tree.search(10, Operation.NEQ).getValue() == 1;
//		assert tree.search(0, Operation.NEQ).getValue() == 1;
//		assert tree.search(-1, Operation.NEQ).getValue() == 1;
//		assert tree.search(100, Operation.NEQ).getValue() == 1;
//		assert tree.search(101, Operation.NEQ).getValue() == 1;
//		assert tree.search(1, Operation.NEQ).getValue() == 2;
//		assert tree.search(10, Operation.GT).getValue() == 11;
//		assert tree.search(10, Operation.GTE).getValue() == 10;
//		assert tree.search(101, Operation.GTE) == null;
//		assert tree.search(10, Operation.LT).getValue() == 1;
//		assert tree.search(0, Operation.LT) == null;
//		assert tree.search(-1, Operation.LTE) == null;
//		assert tree.search(32, Operation.GT).getValue() == 33;	
//		assert tree.search(100, Operation.GT) == null;	
//		
//		for(int i = 1 ; i <= 100 ; ++ i) {
//			System.out.println(tree.search(i, Operation.EQ) + ", " +
//					tree.search(i, Operation.GT) + ", " +
//					tree.search(i, Operation.GTE) + ", " +
//					tree.search(i, Operation.LT) + ", " +
//					tree.search(i, Operation.LTE));
//		}
//		
//		tree = new BTree<>();
//		for(int i = 1 ; i <= 100 ; ++ i)
//			tree.insert(i % 10, i % 10);
//		
//		for(int i = 1 ; i <= 100 ; ++ i) {
//			System.out.println(tree.search(i, Operation.EQ) + ", " +
//					tree.search(i, Operation.GT) + ", " +
//					tree.search(i, Operation.GTE) + ", " +
//					tree.search(i, Operation.LT) + ", " +
//					tree.search(i, Operation.LTE));
//		}
//		
//	}
	
}

package com.tvd12.dahlia.core.test.btree;

import java.util.concurrent.atomic.AtomicInteger;

import org.testng.annotations.Test;

import com.tvd12.dahlia.core.btree.BTree;
import com.tvd12.dahlia.core.tree.Tree;
import com.tvd12.dahlia.core.tree.TreeWalker;

public class BTreeSequentialAccessTest {

	@Test
	public void test() {
		BTree<Integer, Integer> tree = new BTree<>(4);
		System.out.println("sequential access 1st:\n");
		tree.walk(e ->  {
			System.out.print(e + " ");
		});
		tree.walkReverse(e ->  {
			System.out.print(e + " ");
		});
		for(int i = 1 ; i <= 100 ; ++ i)
			tree.insert(i, i);
		
		System.out.println("\nsequential access 2nd:\n");
		tree.walk(e ->  {
			System.out.print(e + " ");
		});
		
		System.out.println("\nsequential access 3rd:\n");
		for(int i = 1 ; i <= 100 ; ++ i) {
			final int index = i;
			tree.walk(new TreeWalker<Integer, Integer>() {
				
				AtomicInteger count = new AtomicInteger();
				
				@Override
				public void accept(Tree.Entry<Integer, Integer> e) {
					System.out.print(e + " ");
					count.incrementAndGet();
				}
				
				@Override
				public boolean next() {
					return count.get() < index;
				}
			});
			System.out.println();
		}
		
		System.out.println("\n\n========================================\n\n");
		
		for(int i = 1 ; i <= 100 ; ++ i) {
			final int index = i;
			tree.walkReverse(new TreeWalker<Integer, Integer>() {
				
				AtomicInteger count = new AtomicInteger();
				
				@Override
				public void accept(Tree.Entry<Integer, Integer> e) {
					System.out.print(e + " ");
					count.incrementAndGet();
				}
				
				@Override
				public boolean next() {
					return count.get() < index;
				}
			});
			System.out.println();
		}
		
		System.out.println("\n\n========================================\n\n");
		
		tree.walkReverse(new TreeWalker<Integer, Integer>() {
			
			@Override
			public void accept(Tree.Entry<Integer, Integer> e) {
				System.out.print(e + " ");
			}
			
			@Override
			public boolean next() {
				return true;
			}
		});
		
		tree.delete(20);
		tree.delete(21);
		tree.delete(22);
		
		System.out.println("\n\n========================================\n\n");
		
		tree.walkReverse(new TreeWalker<Integer, Integer>() {
			
			@Override
			public void accept(Tree.Entry<Integer, Integer> e) {
				System.out.print(e + " ");
			}
			
			@Override
			public boolean next() {
				return true;
			}
		});
	}
	
}

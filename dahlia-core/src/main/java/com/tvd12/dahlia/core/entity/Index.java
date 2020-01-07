package com.tvd12.dahlia.core.entity;

import com.tvd12.dahlia.core.btree.BTree;
import com.tvd12.dahlia.core.function.IdConsumer;
import com.tvd12.dahlia.core.tree.Tree;
import com.tvd12.dahlia.core.tree.Tree.Entry;
import com.tvd12.dahlia.core.tree.TreeWalker;

public class Index {

	protected final Tree<Comparable, Comparable> tree;
	
	public Index() {
		this.tree = new BTree<>();
	}
	
	public Comparable find(Comparable key) {
		Comparable id = tree.get(key);
		return id;
	}
	
	public void insert(Comparable key, Comparable id) {
		this.tree.insert(key, id);
	}
	
	public Comparable remove(Comparable key) {
		return tree.remove(key);
	}
	
	public void forEach(IdConsumer consumer) {
		this.tree.walk(new TreeWalker<Comparable, Comparable>() {
			@Override
			public void accept(Entry<Comparable, Comparable> entry) {
				consumer.accept(entry.getValue());
			}
			@Override
			public boolean next() {
				return consumer.next();
			}
		});
	}
	
}

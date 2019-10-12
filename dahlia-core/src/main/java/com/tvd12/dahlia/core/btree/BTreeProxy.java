package com.tvd12.dahlia.core.btree;

import com.tvd12.dahlia.core.btree.BTree.Node;

public class BTreeProxy {

	protected final BTree tree;
	
	public BTreeProxy(BTree tree) {
		this.tree = tree;
	}
	
	public NodeProxy getRoot() {
		if(tree.root == null)
			return null;
		return new NodeProxy(tree.root);
	}
	
	public int getMinEntry() {
		return tree.minEntry;
	}
	
	public int getMaxDegree() {
		return tree.maxDegree;
	}
	
	public int getSplitIndex() {
		return tree.splitIndex;
	}
	
	public static class NodeProxy {
		
		protected final Node node;
		
		public NodeProxy(Node node) {
			this.node = node;
		}
		
		public int getEntryCount() {
			return node.entryCount;
		}
		
		public int getChildCount() {
			if(node.entryCount == 0)
				return 0;
			return node.entryCount + 1;
		}
		
		public Integer getEntry(int index) {
			if(node.entries[index] == null)
				return null;
			return node.entries[index];
		}
		
		public NodeProxy getChildren(int index) {
			if(node.children[index] == null)
				return null;
			return new NodeProxy(node.children[index]);
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			for(int i = 0 ; i < node.entryCount ; ++i) {
				builder.append(node.entries[i]);
				if(i < node.entryCount - 1)
					builder.append(" ");
			}
			return builder.toString();
		}
	}
	
}

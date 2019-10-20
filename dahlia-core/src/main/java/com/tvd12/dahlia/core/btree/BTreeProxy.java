package com.tvd12.dahlia.core.btree;

import java.util.ArrayList;
import java.util.List;

import com.tvd12.dahlia.core.btree.BTree.Node;
import com.tvd12.ezyfox.util.EzyEquals;

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
		
		public NodeProxy getParent() {
			if(node.parent == null)
				return null;
			return new NodeProxy(node.parent);
		}
		
		public int getEntryCount() {
			return node.entryCount;
		}
		
		public List<NodeProxy> getChildren() {
			List<NodeProxy> children = new ArrayList<>();
			for(int i = 0 ;; ++ i) {
				Node child = node.children[i];
				if(child == null)
					break;
				if(i > node.entryCount)
					break;
				children.add(new NodeProxy(child));
			}
			return children;
		}
		
		public Integer getEntry(int index) {
			if(node.entries[index] == null)
				return null;
			return node.entries[index];
		}
		
		@Override
		public boolean equals(Object obj) {
			return new EzyEquals<NodeProxy>()
					.function(t -> t.node)
					.isEquals(this, obj);
		}
		
	}
	
}

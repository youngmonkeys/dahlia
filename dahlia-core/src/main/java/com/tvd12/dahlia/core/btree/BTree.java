package com.tvd12.dahlia.core.btree;

public class BTree {

	protected Node root;
	protected final int minEntry;
	protected final int maxDegree;
	protected final int splitIndex;
	
	public BTree() {
		this(2);
	}
	
	public BTree(int minDegree) {
		this.root = null;
		this.maxDegree = minDegree * 2 - 1;
		this.minEntry = minDegree - 1;
		this.splitIndex = minDegree - 1;
	}
	
	public void insert(int entry) {
		if(root == null) {
			root = new Node(maxDegree);
			root.entries[0] = entry;
			root.entryCount = 1;
		}
		else {
			insertToNode(root, entry);
		}
	}
	
	private void insertToNode(Node node, int entry) {
		if(node.leaf) {
			insertNewEntry(node, entry);
			postInsert(node);
		}
		else {
			Node childToInsert = findChildToInsert(node, entry);
			insertToNode(childToInsert, entry);
		}
	}
	
	private void insertNewEntry(Node node, int entry) {
		int entryCount = node.entryCount;
		++ node.entryCount;
		for(int i = entryCount ; i > 0 ; --i) {
			int prev = node.entries[i - 1];
			if(prev <= entry) {
				node.entries[i] = entry;
				return;
			}
			node.entries[i] = prev;
		}
		node.entries[0] = entry;
	}
	
	private Node findChildToInsert(Node node, int entry) {
		int childIndex = 0;
		while(childIndex < node.entryCount) {
			int compareResult = entry - node.entries[childIndex];
			if(compareResult <= 0)
				break;
			++ childIndex;
		}
		return node.children[childIndex];
	}
	
	private void postInsert(Node node) {
		if(node.entryCount < maxDegree)
			return;
		if(node.parent == null) {
			root = splitNode(node);
		}
		else {
			Node newNode = splitNode(node);
			postInsert(newNode);
		}
	}
	
	private Node splitNode(Node node) {
		Node rightNode = newRightNode(node);
		int risingEntry = node.entries[splitIndex];
		
		if(node.parent != null) {
			Node parent = node.parent;
			int beforeIndexOfNode = beforeIndexOfNodeInParent(node);
			moveNodeItemsToRight(parent, beforeIndexOfNode);
			insertRisingEntry(parent, risingEntry, beforeIndexOfNode);
			parent.children[beforeIndexOfNode + 1] = rightNode;
			rightNode.parent = parent;
		}
		
		moveItemsToRightNode(node, rightNode);
		node.entryCount = splitIndex;
		if(node.parent != null)
			return node.parent;
		Node newRoot = createNewRoot(node, rightNode, risingEntry);
		return newRoot;
	}
	
	private Node newRightNode(Node node) {
		Node rightNode = new Node(maxDegree);
		rightNode.entryCount = node.entryCount - splitIndex - 1;
		return rightNode;
	}
	
	private int beforeIndexOfNodeInParent(Node node) {
		Node currentParent = node.parent;
		int index = 0;
		int childCount = currentParent.entryCount + 1;
		while(index < childCount &&
				currentParent.children[index] != node) {
			++ index;
		}
		if(index == childCount)
			throw new IllegalStateException("hmm!!!, this a magic error");
		return index;
	}
	
	private void moveNodeItemsToRight(Node node, int nodeSplitIndex) {
		for(int i = node.entryCount ; i > nodeSplitIndex ; --i)
			node.children[i + 1] = node.children[i];

		for(int i = node.entryCount ; i > nodeSplitIndex ; --i)
			node.entries[i] = node.entries[i - 1];
	}
	
	private void insertRisingEntry(Node node, int risingEntry, int splitIndex) {
		node.entries[splitIndex] = risingEntry;
		++ node.entryCount;
	}
	
	private void moveItemsToRightNode(Node node, Node rightNode) {
		int childCount = node.entryCount + 1;
		int childStartIndex = splitIndex + 1;
		for(int i = childStartIndex ; i < childCount ; ++i) {
			Node child = node.children[i];
			if(child != null) {
				child.parent = rightNode;
				rightNode.leaf = false;
			}
			node.children[i] = null;
			rightNode.children[i - childStartIndex] = child;
		}
		for(int i = childStartIndex ; i < node.entryCount ; ++i)
			rightNode.entries[i - childStartIndex] = node.entries[i];
	}
	
	private Node createNewRoot(Node node, Node rightNode, int risingEntry) {
		Node leftNode = node;
		Node newRoot = new Node(maxDegree);
		newRoot.leaf = false;
		newRoot.entries[0] = risingEntry;
		newRoot.entryCount = 1;
		newRoot.children[0] = leftNode;
		newRoot.children[1] = rightNode;
		leftNode.parent = newRoot;
		rightNode.parent = newRoot;
		return newRoot;
	}
	
	public void accept(BTreeVisitor visitor) {
		visitor.visit(new BTreeProxy(this));
	}
	
	public static class Node {
		
		protected boolean leaf;
		protected Node parent;
		protected int entryCount;
		
		protected final Integer[] entries;
		protected final Node[] children;
		
		private Node(int maxDegree) {
			this.leaf = true;
			this.parent = null;
			this.entryCount = 0;
			this.entries = new Integer[maxDegree];
			this.children = new Node[maxDegree + 1];
		}
		
	}
}

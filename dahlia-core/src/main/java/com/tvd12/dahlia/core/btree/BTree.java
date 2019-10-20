package com.tvd12.dahlia.core.btree;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class BTree {

	protected Node root;
	protected final int minEntry;
	protected final int maxDegree;
	protected final int splitIndex;
	
	// ====================== constructor ===============
	public BTree() {
		this(2);
	}
	
	public BTree(int minDegree) {
		this.root = null;
		this.maxDegree = minDegree * 2 - 1;
		this.minEntry = minDegree - 1;
		this.splitIndex = minDegree - 1;
	}
	
	// ====================== insert ===============
	public void insert(int entry) {
		if(root == null) {
			root = new Node(maxDegree);
			root.entries[0] = entry;
			root.entryCount = 1;
		}
		else {
			System.out.println("before insert: " + entry + ", current:\n" + this);
			insertToNode(root, entry);
			System.out.println("after insert: " + entry + ", current:\n" + this);
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
			int indexOfNode = indexOfNodeInParent(node);
			moveItemsToRight(parent, indexOfNode);
			insertRisingEntry(parent, risingEntry, indexOfNode);
			parent.children[indexOfNode + 1] = rightNode;
			rightNode.parent = parent;
		}
		
		moveItemsToRightNode(node, rightNode);
		node.entries[splitIndex] = null;
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
	
	private int indexOfNodeInParent(Node node) {
		Node currentParent = node.parent;
		int index = 0;
		int childCount = currentParent.entryCount + 1;
		while(index < childCount &&
				currentParent.children[index] != node) {
			++ index;
		}
		if(index == childCount)
			throw new IllegalStateException("hmm!!!, this is a magic error");
		return index;
	}
	
	private void moveItemsToRight(Node node, int nodeSplitIndex) {
		moveChildrenToRight(node, nodeSplitIndex);
		moveEntriesToRight(node, nodeSplitIndex);
	}
	
	private void moveChildrenToRight(Node node, int nodeSplitIndex) {
		moveChildrenToRight(node, node.entryCount, nodeSplitIndex);
	}
	
	private void moveChildrenToRight(Node node, int startIndex, int nodeSplitIndex) {
		for(int i = startIndex ; i > nodeSplitIndex ; --i)
			node.children[i + 1] = node.children[i];
	}
	
	private void moveEntriesToRight(Node node, int nodeSplitIndex) {
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
		for(int i = childStartIndex ; i < node.entryCount ; ++i) {
			rightNode.entries[i - childStartIndex] = node.entries[i];
			node.entries[i] = null;
		}
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
	
	// ====================== delete ===============
	public void delete(int key) {
		deleteFromNode(root, key);
	}
	
	private final AtomicInteger doDeleteCount = new AtomicInteger();
	private void deleteFromNode(Node node, int key) {
		int count = doDeleteCount.incrementAndGet();
		System.out.println("============ start do delete: " + count + " =========");
		System.out.println("tree: " + node + ", val: " + key + ", current:\n" + this);
		if(node == null)
			return;
		int entryIndex = findEntryIndex(node, key);
		if(entryIndex == node.entryCount) {
			if(node.leaf)
				return;
			deleteFromNode(node.children[node.entryCount], key);
		}
		else if(node.entries[entryIndex] > key) {
			if(node.leaf)
				return;
			deleteFromNode(node.children[entryIndex], key);
		}
		else {
			if(node.leaf) {
				moveEntriesToLeft(node, entryIndex);
				-- node.entryCount;
				postDelete(node);
			}
			else {
				Node maxNode = getMaxLeafNode(node.children[entryIndex]);
				node.entries[entryIndex] = maxNode.entries[maxNode.entryCount - 1];
				-- maxNode.entryCount;
				postDelete(maxNode);
			}
		}
		System.out.println("do delete final current:\n" + this);
		System.out.println("============ end do delete: " + count + " =========");
	}
	
	private int findEntryIndex(Node node, int key) {
		int index = 0;
		while(index < node.entryCount && node.entries[index] < key)
			++ index;
		return index;
	}
	
	private void moveItemsToLeft(Node node, int leftIndex) {
		moveChildrenToLeft(node, leftIndex);
		moveEntriesToLeft(node, leftIndex - 1);
	}
	
	private void moveChildrenToLeft(Node node, int leftIndex) {
		for(int i = leftIndex ; i < node.entryCount ; ++i)
			node.children[i] = node.children[i + 1];
	}
	
	private void moveEntriesToLeft(Node node, int leftIndex) {
		int maxIndex = node.entryCount - 1;
		for(int i = leftIndex ; i < maxIndex ; ++i)
			node.entries[i] = node.entries[i + 1];
	}
	
	private Node getMaxLeafNode(Node node) {
		Node maxNode = node;
		while(!maxNode.leaf)
			maxNode = maxNode.children[maxNode.entryCount];
		return maxNode;
	}
	
	AtomicInteger repairAfterDeleteCount = new AtomicInteger();
	private void postDelete(Node node) {
		int count = repairAfterDeleteCount.incrementAndGet();
		System.out.println("========== start repairAfterDelete " + count + " ==============");
		System.out.println("tree: " + node + ", current:\n" + this);
		if(node.entryCount >= minEntry)
			return;
		System.out.println("1. tree.numKeys < this.min_keys: " + node.entryCount + " < " + this.minEntry);
		if(node.parent == null) {
			System.out.println("2. tree.parent == null");
			if(node.entryCount == 0) {
				System.out.println("3. tree.numKeys == 0");
				root = root.children[0];
				if(root != null)
					root.parent = null;
				System.out.println("4. this.treeRoot = " + this.root);
			}
		}
		else {
			System.out.println("5. else tree.parent != null");
			Node parentNode = node.parent;
			int indexOfNode = indexOfNodeInParent(node);
			System.out.println("6. parentIndex: " + indexOfNode + ", parentNode: " + parentNode);
			if(indexOfNode > 0 && 
					parentNode.children[indexOfNode - 1].entryCount > minEntry) {
				System.out.println("7. ");
				stealFromLeft(node, indexOfNode);
			}
			else if(indexOfNode < parentNode.entryCount &&
					parentNode.children[indexOfNode + 1].entryCount > minEntry) {
				System.out.println("8. ");
				stealFromRight(node, indexOfNode);
			}
			else if(indexOfNode == 0) {
				System.out.println("9. ");
				Node nextNode = mergeRight(node);
				postDelete(nextNode.parent);
			}
			else {
				System.out.println("10. ");
				Node nextNode = mergeRight(parentNode.children[indexOfNode - 1]);
				postDelete(nextNode.parent);
			}
		}
		System.out.println("repairAfterDelete final current:\n" + this);
		System.out.println("========== end repairAfterDelete " + count + " ==============");
	}
	
	AtomicInteger stealFromLeftCount = new AtomicInteger();
	private void stealFromLeft(Node node, int indexOfNodeInParent) {
		int count = stealFromLeftCount.incrementAndGet();
		System.out.println("========== start stealFromLeft " + count + " ==============");
		Node parentNode = node.parent;
		System.out.println("tree: " + node + ", parentIndex: " + indexOfNodeInParent + ", current:\n" + this);
		moveEntriesToRight(node, 0);
		++ node.entryCount;
		
		Node leftSib = parentNode.children[indexOfNodeInParent - 1];
		
		System.out.println("1. tree: " + node + ", parentNode: " + parentNode + ", leftSib: " + leftSib);
		
		if(!leftSib.leaf) {
			moveChildrenToRight(node, node.entryCount - 1, 0);
			System.out.println("2. tree: " + node);
			node.children[0] = leftSib.children[leftSib.entryCount];
			leftSib.children[leftSib.entryCount] = null;
			node.children[0].parent = node;
			System.out.println("3. tree: " + node + ", leftSib: " + leftSib);
		}
		
		node.entries[0] = parentNode.entries[indexOfNodeInParent - 1];
		parentNode.entries[indexOfNodeInParent - 1] = leftSib.entries[leftSib.entryCount - 1];
		-- leftSib.entryCount;
		System.out.println("stealFromLeft final current:\n" + this);
		System.out.println("========== end stealFromLeft " + count + " ==============");
	}
	
	AtomicInteger stealFromRightCount = new AtomicInteger();
	private void stealFromRight(Node node, int indexOfNodeInParent) {
		int count = stealFromRightCount.incrementAndGet();
		System.out.println("========== start stealFromRight " + count + " ==============");
		Node parentNode = node.parent;
		Node rightSib = parentNode.children[indexOfNodeInParent + 1];
		System.out.println("2. tree: " + node + ", parentNode: " + parentNode + ", parentIndex: " + indexOfNodeInParent + ", rightSib: " + rightSib);
		++ node.entryCount;
		node.entries[node.entryCount - 1] = parentNode.entries[indexOfNodeInParent];
		parentNode.entries[indexOfNodeInParent] = rightSib.entries[0];
		System.out.println("3. tree: " + node + ", parentNode: " + parentNode);
		if(!node.leaf) {
			node.children[node.entryCount] = rightSib.children[0];
			node.children[node.entryCount].parent = node;
			moveChildrenToLeft(rightSib, 0);
			System.out.println("4. tree: " + node + ", rightSib: " + rightSib);
		}
		moveEntriesToLeft(rightSib, 0);
		-- rightSib.entryCount;
		System.out.println("5. tree: " + node + ", rightSib: " + rightSib);
		System.out.println("stealFromRight final current:\n" + this);
		System.out.println("========== end stealFromRight " + count + " ==============");
	}
	
	AtomicInteger mergeRightCount = new AtomicInteger(); 
	private Node mergeRight(Node node) {
		int count = mergeRightCount.incrementAndGet();
		System.out.println("================== start mergeRight " + count + " =============");
		System.out.println("tree: " + node + ", current:\n" + this);
		Node parentNode = node.parent;
		int indexOfNode = indexOfNodeInParent(node);
		System.out.println("tree: " + node + ", parentNode: " + parentNode + ", parentIndex: " + indexOfNode);
		Node rightSib = parentNode.children[indexOfNode + 1];
		node.entries[node.entryCount] = parentNode.entries[indexOfNode];
		copyEntries(rightSib, node, node.entryCount + 1);
		System.out.println("1. tree: " + node + ", rightSib: " + rightSib + ", parentNode: " + parentNode);
		if(!node.leaf)
			copyChildren(rightSib, node, node.entryCount + 1);
		moveItemsToLeft(parentNode, indexOfNode + 1);
		parentNode.children[parentNode.entryCount] = null;
		-- parentNode.entryCount;
		node.entryCount = node.entryCount + rightSib.entryCount + 1;
		System.out.println("4. tree: " + node + ", parentNode: " + parentNode);
		System.out.println("mergeRight final current:\n" + this.toString());
		System.out.println("================== end mergeRight " + count + " =============");
		return node;
	}
	
	private void copyEntries(Node from, Node to, int toStartIndex) {
		for(int i = 0 ; i < from.entryCount ; ++i)
			to.entries[toStartIndex + i] = from.entries[i];
	}
	
	private void copyChildren(Node from, Node to, int toStartIndex) {
		int childCount = from.entryCount + 1;
		for(int i = 0 ; i < childCount ; ++i) {
			int index = toStartIndex + i;
			to.children[index] = from.children[i];
			to.children[index].parent = to;
		}
	}
	
	// ====================== search ===============
	public Integer search(int key) {
		Integer value = searchInNode(root, key);
		return value;
	}
	
	private Integer searchInNode(Node node, int key) {
		if(node == null)
			return null;
		int index = findEntryIndex(node, key);
		if(index == node.entryCount) {
			if(node.leaf)
				return null;
			return searchInNode(node.children[node.entryCount], key);
		}
		if(node.entries[index] > key) {
			if(node.leaf)
				return null;
			return searchInNode(node.children[index], key);
		}
		Integer value = node.entries[index];
		return value;
	}
	
	// ====================== sequential access ===============
	public void walk(BTreeWalker walker) {
		walkInNode(root, walker);
	}
	
	private void walkInNode(Node node, BTreeWalker walker) {
		if(node == null)
			return;
		
		if(node.leaf) {
			for(int i = 0 ; i < node.entryCount ; ++i) {
				if(walker.next())
					walker.accept(node.entries[i]);
				else
					break;
			}
		}
		else {
			walkInNode(node.children[0], walker);
			for(int i = 0 ; i < node.entryCount ; ++i) {
				if(walker.next()) {
					walker.accept(node.entries[i]);
					walkInNode(node.children[i + 1], walker);
				}
			}
		}
	}
	
	// ====================== utilities ===============
	public void accept(BTreeVisitor visitor) {
		visitor.visit(new BTreeProxy(this));
	}
	
	// ====================== to string ===============
	@Override
	public String toString() {
		BTreePrinter printer = new BTreePrinter();
		accept(printer);
		return printer.print();
	}
	
	public static class Node {
		
		protected boolean leaf;
		protected Node parent;
		protected int entryCount;
		
		protected final Integer[] entries;
		protected final Node[] children;
		protected final long id;
		protected final static AtomicLong ID_GENTOR = new AtomicLong();
		
		public Node(int maxDegree) {
			this.leaf = true;
			this.parent = null;
			this.entryCount = 0;
			this.entries = new Integer[maxDegree];
			this.children = new Node[maxDegree + 1];
			this.id = ID_GENTOR.incrementAndGet();
		}
		
		public void accept(BTreeVisitor.NodeVisitor vistor) {
			vistor.visit(new BTreeProxy.NodeProxy(this));
		}
		
		@Override
		public String toString() {
			BTreePrinter.NodePrinter printer = new BTreePrinter.NodePrinter();
			accept(printer);
			return printer.print();
		}
	}
}

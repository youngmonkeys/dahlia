package com.tvd12.dahlia.core.btree;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicLong;

import com.tvd12.dahlia.core.tree.Tree;
import com.tvd12.dahlia.core.tree.TreeWalker;
import com.tvd12.dahlia.math.Operation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("unchecked")
public class BTree<K, V> extends Tree<K, V> {

	protected Node<K, V> root;
	protected final int minEntry;
	protected final int maxDegree;
	protected final int splitIndex;
	
	// ====================== constructor ===============
	public BTree() {
		this(2);
	}
	
	public BTree(int minDegree) {
		this(minDegree, null);
	}
	
	public BTree(Comparator<K> keyComparator) {
		this(2, keyComparator);
	}
	
	public BTree(int minDegree, Comparator<K> keyComparator) {
		super(keyComparator);
		this.root = null;
		this.maxDegree = minDegree * 2 - 1;
		this.minEntry = minDegree - 1;
		this.splitIndex = minDegree - 1;
	}
	
	// ====================== insert ===============
	@Override
	public void insert(K key, V value) {
		Entry<K, V> entry = new Tree.Entry<>(key, value);
		if(root == null) {
			root = new Node<>(maxDegree);
			root.entries[0] = entry;
			root.entryCount = 1;
		}
		else {
			insertToNode(root, entry);
		}
	}
	
	private void insertToNode(Node<K, V> node, Entry<K, V> entry) {
		if(node.leaf) {
			insertNewEntry(node, entry);
			postInsert(node);
		}
		else {
			Node<K, V> childToInsert = findChildToInsert(node, entry);
			insertToNode(childToInsert, entry);
		}
	}
	
	private void insertNewEntry(Node<K, V> node, Entry<K, V> entry) {
		int entryCount = node.entryCount;
		++ node.entryCount;
		for(int i = entryCount ; i > 0 ; --i) {
			Entry<K, V> prev = node.entries[i - 1];
			if(compareEntry(prev, entry) <= 0) {
				node.entries[i] = entry;
				return;
			}
			node.entries[i] = prev;
		}
		node.entries[0] = entry;
	}
	
	private Node<K, V> findChildToInsert(Node<K, V> node, Entry< K, V> entry) {
		int childIndex = 0;
		while(childIndex < node.entryCount) {
			if(compareEntry(entry, node.entries[childIndex]) <= 0)
				break;
			++ childIndex;
		}
		return node.children[childIndex];
	}
	
	private void postInsert(Node<K, V> node) {
		if(node.entryCount < maxDegree)
			return;
		if(node.parent == null) {
			root = splitNode(node);
		}
		else {
			Node<K, V> newNode = splitNode(node);
			postInsert(newNode);
		}
	}
	
	private Node<K, V> splitNode(Node<K, V> node) {
		Node<K, V> rightNode = newRightNode(node);
		Entry<K, V> risingEntry = node.entries[splitIndex];
		
		if(node.parent != null) {
			Node<K, V> parent = node.parent;
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
		Node<K, V> newRoot = createNewRoot(node, rightNode, risingEntry);
		return newRoot;
	}
	
	private Node<K, V> newRightNode(Node<K, V> node) {
		Node<K, V> rightNode = new Node<>(maxDegree);
		rightNode.entryCount = node.entryCount - splitIndex - 1;
		return rightNode;
	}
	
	private void insertRisingEntry(Node<K, V> node, Entry<K, V> risingEntry, int splitIndex) {
		node.entries[splitIndex] = risingEntry;
		++ node.entryCount;
	}
	
	private Node<K, V> createNewRoot(Node<K, V> node, Node<K, V> rightNode, Entry<K, V> risingEntry) {
		Node<K, V> leftNode = node;
		Node<K, V> newRoot = new Node<>(maxDegree);
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
	@Override
	public V delete(K key) {
		V deletedValue = deleteFromNode(root, key);
		return deletedValue;
	}
	
	private V deleteFromNode(Node<K, V> node, K key) {
		if(node == null)
			return null;
		CompareResult compareResult = findEntryIndex(node, key);
		int entryIndex = compareResult.getIndex();
		if(entryIndex == node.entryCount) {
			if(node.leaf)
				return null;
			return deleteFromNode(node.children[node.entryCount], key);
		}
		else if(compareResult.getResult() > 0) {
			if(node.leaf)
				return null;
			return deleteFromNode(node.children[entryIndex], key);
		}
		else {
			Entry<K, V> deletedEntry = node.entries[entryIndex];
			if(node.leaf) {
				moveEntriesToLeft(node, entryIndex);
				-- node.entryCount;
				postDelete(node);
			}
			else {
				Node<K, V> maxNode = getMaxLeafNode(node.children[entryIndex]);
				node.entries[entryIndex] = maxNode.entries[maxNode.entryCount - 1];
				-- maxNode.entryCount;
				postDelete(maxNode);
			}
			V value = deletedEntry.getValue();
			return value;
		}
	}
	
	private Node<K, V> getMaxLeafNode(Node<K, V> node) {
		Node<K, V> maxNode = node;
		while(!maxNode.leaf)
			maxNode = maxNode.children[maxNode.entryCount];
		return maxNode;
	}
	
	private void postDelete(Node<K, V> node) {
		if(node.entryCount >= minEntry)
			return;
		if(node.parent == null) {
			if(node.entryCount == 0) {
				root = root.children[0];
				if(root != null)
					root.parent = null;
			}
		}
		else {
			Node<K, V> parentNode = node.parent;
			int indexOfNode = indexOfNodeInParent(node);
			if(indexOfNode > 0 && 
					parentNode.children[indexOfNode - 1].entryCount > minEntry) {
				stealFromLeft(node, indexOfNode);
			}
			else if(indexOfNode < parentNode.entryCount &&
					parentNode.children[indexOfNode + 1].entryCount > minEntry) {
				stealFromRight(node, indexOfNode);
			}
			else if(indexOfNode == 0) {
				Node<K, V> nextNode = mergeRight(node);
				postDelete(nextNode.parent);
			}
			else {
				Node<K, V> nextNode = mergeRight(parentNode.children[indexOfNode - 1]);
				postDelete(nextNode.parent);
			}
		}
	}
	
	private void stealFromLeft(Node<K, V> node, int indexOfNodeInParent) {
		Node<K, V> parentNode = node.parent;
		moveEntriesToRight(node, 0);
		++ node.entryCount;
		
		Node<K, V> leftSib = parentNode.children[indexOfNodeInParent - 1];
		
		if(!leftSib.leaf) {
			moveChildrenToRight(node, node.entryCount - 1, 0);
			node.children[0] = leftSib.children[leftSib.entryCount];
			leftSib.children[leftSib.entryCount] = null;
			node.children[0].parent = node;
		}
		
		node.entries[0] = parentNode.entries[indexOfNodeInParent - 1];
		parentNode.entries[indexOfNodeInParent - 1] = leftSib.entries[leftSib.entryCount - 1];
		-- leftSib.entryCount;
	}
	
	private void stealFromRight(Node<K, V> node, int indexOfNodeInParent) {
		Node<K, V> parentNode = node.parent;
		Node<K, V> rightSib = parentNode.children[indexOfNodeInParent + 1];
		++ node.entryCount;
		node.entries[node.entryCount - 1] = parentNode.entries[indexOfNodeInParent];
		parentNode.entries[indexOfNodeInParent] = rightSib.entries[0];
		if(!node.leaf) {
			node.children[node.entryCount] = rightSib.children[0];
			node.children[node.entryCount].parent = node;
			moveChildrenToLeft(rightSib, 0);
		}
		moveEntriesToLeft(rightSib, 0);
		-- rightSib.entryCount;
	}
	
	private Node<K, V> mergeRight(Node<K, V> node) {
		Node<K, V> parentNode = node.parent;
		int indexOfNode = indexOfNodeInParent(node);
		Node<K, V> rightSib = parentNode.children[indexOfNode + 1];
		node.entries[node.entryCount] = parentNode.entries[indexOfNode];
		copyEntries(rightSib, node, node.entryCount + 1);
		if(!node.leaf)
			copyChildren(rightSib, node, node.entryCount + 1);
		moveItemsToLeft(parentNode, indexOfNode + 1);
		parentNode.children[parentNode.entryCount] = null;
		-- parentNode.entryCount;
		node.entryCount = node.entryCount + rightSib.entryCount + 1;
		return node;
	}
	
	// ====================== search ===============
	public Entry<K, V> search(K key) {
		Entry<K, V> entry = searchInNode(root, key);
		return entry;
	}
	
	private Entry<K, V> searchInNode(Node<K, V> node, K key) {
		if(node == null)
			return null;
		CompareResult compareResult = findEntryIndex(node, key);
		int entryIndex = compareResult.getIndex();
		if(entryIndex == node.entryCount) {
			if(node.leaf)
				return null;
			return searchInNode(node.children[node.entryCount], key);
		}
		if(compareResult.getResult() > 0) {
			if(node.leaf)
				return null;
			return searchInNode(node.children[entryIndex], key);
		}
		Entry<K, V> entry = node.entries[entryIndex];
		return entry;
	}
	
	@Override
	public Entry<K, V> search(K key, Operation op) {
		Entry<K, V> entry = searchInNode(root, key, op);
        return entry;
	}
	
	private Entry<K, V> searchInNode(Node<K, V> node, K key, Operation op) {
        if(node == null)
            return null;
        int entryIndex = 0;
        int compareResult = 0;
        while(entryIndex < node.entryCount) {
            compareResult = compareEntryKey(node.entries[entryIndex], key);
            if(op == Operation.EQ || op == Operation.GTE) {
                if (compareResult < 0)
                    ++entryIndex;
                else
                    break;
            }
            else if (op == Operation.GT) {
                if (compareResult <= 0)
                    ++entryIndex;
                else
                    break;
            }
            else {
                break;
            }
        }

        if(entryIndex == 0) {
            if(op == Operation.LT) {
                if(compareResult < 0)
                    return searchInNodeFit(node, key, op, entryIndex);
                return searchInNodeCheckLeaf(node, key, op, entryIndex);

            }
            else if(op == Operation.LTE) {
                if(compareResult <= 0)
                    return searchInNodeFit(node, key, op, entryIndex);
                return searchInNodeCheckLeaf(node, key, op, entryIndex);
            }
        }

        if(entryIndex < node.entryCount) {
            if (op == Operation.EQ) {
                if (compareResult == 0)
                    return node.entries[entryIndex];
            }
            else if (op == Operation.GT) {
            	return searchInNodeFit(node, key, op, entryIndex);
            }
            else if (op == Operation.GTE) {
            	return searchInNodeFit(node, key, op, entryIndex);
            }
            else {
            	if(compareResult != 0)
            		return searchInNodeFit(node, key, op, entryIndex);
            }
        }

        return searchInNodeCheckLeaf(node, key, op, entryIndex);
    }

    private Entry<K, V> searchInNodeFit(Node<K, V> node, K key, Operation op, int entryIndex) {
    	Entry<K, V> match = searchInNodeCheckLeaf(node, key, op, entryIndex);
        if(match != null)
        	return match;	
        return node.entries[entryIndex];
    }

    private Entry<K, V> searchInNodeCheckLeaf(Node<K, V> node, K key, Operation op, int entryIndex) {
        if(node.leaf)
            return null;
        return searchInNode(node.children[entryIndex], key, op);
    }
	
	// ====================== sequential access ===============
	@Override
	public void walk(TreeWalker<K, V> walker) {
		walkInNode(root, walker);
	}
	
	private void walkInNode(Node<K, V> node, TreeWalker<K, V> walker) {
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
	
	@Override
	public void walkReverse(TreeWalker<K, V> walker) {
		walkReverseInNode(root, walker);
	}
	
	private void walkReverseInNode(Node<K, V> node, TreeWalker<K, V> walker) {
		if(node == null)
			return;
		
		if(node.leaf) {
			for(int i = node.entryCount - 1 ; i >= 0 ; --i) {
				if(walker.next())
					walker.accept(node.entries[i]);
				else
					break;
			}
		}
		else {
			walkReverseInNode(node.children[node.entryCount], walker);
			for(int i = node.entryCount - 1 ; i >= 0 ; --i) {
				if(walker.next()) {
					walker.accept(node.entries[i]);
					walkReverseInNode(node.children[i], walker);
				}
			}
		}
	}
	
	// ====================== clear ===============
	@Override
	public void clear() {
		this.clearNode(root);
		this.root = null;
	}
	
	private void clearNode(Node<K, V> node) {
		if (node != null) {
			if (!node.leaf) {
				for (int i = 0; i <= node.entryCount; i++) {
					this.clearNode(node.children[i]);
					node.children[i].clear();
				}
			}
		}
	}
	
	// ====================== map methods ===============
	@Override
	public boolean isEmpty() {
		return root == null;
	}
	
	// ====================== utilities ===============
	public void accept(BTreeVisitor visitor) {
		visitor.visit(new BTreeProxy<>(this));
	}
	
	
	private int indexOfNodeInParent(Node<K, V> node) {
		Node<K, V> currentParent = node.parent;
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
	
	private void moveItemsToRight(Node<K, V> node, int nodeSplitIndex) {
		moveChildrenToRight(node, nodeSplitIndex);
		moveEntriesToRight(node, nodeSplitIndex);
	}
	
	private void moveChildrenToRight(Node<K, V> node, int nodeSplitIndex) {
		moveChildrenToRight(node, node.entryCount, nodeSplitIndex);
	}
	
	private void moveChildrenToRight(Node<K, V> node, int startIndex, int nodeSplitIndex) {
		for(int i = startIndex ; i > nodeSplitIndex ; --i)
			node.children[i + 1] = node.children[i];
	}
	
	private void moveEntriesToRight(Node<K, V> node, int nodeSplitIndex) {
		for(int i = node.entryCount ; i > nodeSplitIndex ; --i)
			node.entries[i] = node.entries[i - 1];
	}
	
	private void moveItemsToRightNode(Node<K, V> node, Node<K, V> rightNode) {
		int childCount = node.entryCount + 1;
		int childStartIndex = splitIndex + 1;
		for(int i = childStartIndex ; i < childCount ; ++i) {
			Node<K, V> child = node.children[i];
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
	
	private void moveItemsToLeft(Node<K, V> node, int leftIndex) {
		moveChildrenToLeft(node, leftIndex);
		moveEntriesToLeft(node, leftIndex - 1);
	}
	
	private void moveChildrenToLeft(Node<K, V> node, int leftIndex) {
		for(int i = leftIndex ; i < node.entryCount ; ++i)
			node.children[i] = node.children[i + 1];
	}
	
	private void moveEntriesToLeft(Node<K, V> node, int leftIndex) {
		int maxIndex = node.entryCount - 1;
		for(int i = leftIndex ; i < maxIndex ; ++i)
			node.entries[i] = node.entries[i + 1];
	}
	
	private void copyEntries(Node<K, V> from, Node<K, V> to, int toStartIndex) {
		for(int i = 0 ; i < from.entryCount ; ++i)
			to.entries[toStartIndex + i] = from.entries[i];
	}
	
	private void copyChildren(Node<K, V> from, Node<K, V> to, int toStartIndex) {
		int childCount = from.entryCount + 1;
		for(int i = 0 ; i < childCount ; ++i) {
			int index = toStartIndex + i;
			to.children[index] = from.children[i];
			to.children[index].parent = to;
		}
	}
	
	private CompareResult findEntryIndex(Node<K, V> node, K key) {
		int index = 0;
		while(index < node.entryCount) {
			int cresult = compareEntryKey(node.entries[index], key);
			if(cresult < 0)
				++ index;
			else
				return new CompareResult(index, cresult);
		}
		return new CompareResult(index);
	}
	
	// ====================== to string ===============
	@Override
	public String toString() {
		BTreePrinter printer = new BTreePrinter();
		accept(printer);
		return printer.print();
	}
	
	public static class Node<K, V> {
		
		protected boolean leaf;
		protected Node<K, V> parent;
		protected int entryCount;
		
		protected final Node<K, V>[] children;
		protected final Tree.Entry<K, V>[] entries;
		protected final long id;
		protected final static AtomicLong ID_GENTOR = new AtomicLong();
		
		public Node(int maxDegree) {
			this.leaf = true;
			this.parent = null;
			this.entryCount = 0;
			this.entries = new Tree.Entry[maxDegree];
			this.children = new Node[maxDegree + 1];
			this.id = ID_GENTOR.incrementAndGet();
		}
		
		public void accept(BTreeVisitor.NodeVisitor vistor) {
			vistor.visit(new BTreeProxy.NodeProxy<>(this));
		}
		
		@Override
		public String toString() {
			BTreePrinter.NodePrinter printer = new BTreePrinter.NodePrinter();
			accept(printer);
			return printer.print();
		}
		
		public void clear() {
			for(int i = 0 ; i < entries.length ; ++ i)
				entries[i] = null;
			for(int i = 0 ; i < children.length ; ++ i)
				children[i] = null;
		}
	}
	
	@Getter
	@AllArgsConstructor
	public static class CompareResult {
		
		protected final Integer index;
		protected final Integer result;
		
		public CompareResult(Integer index) {
			this(index, null);
		}
		
	}
}

package com.tvd12.dahlia.core.btree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tvd12.dahlia.core.btree.BTreeProxy.NodeProxy;

public class BTreePrinter implements BTreeVisitor {

	protected final List<List<BTreeProxy.NodeProxy>> treeTiers;
	
	public BTreePrinter() {
		this.treeTiers = new ArrayList<>();
	}
	
	@Override
	public synchronized void visit(BTreeProxy tree) {
		BTreeProxy.NodeProxy root = tree.getRoot();
		treeTiers.clear();
		if(root == null)
			return;
		treeTiers.add(Arrays.asList(root));
		visitNode(root, treeTiers, 1);
		
	}
	
	public synchronized String print() {
		if(treeTiers.isEmpty())
			return "";
		int tierIndex = 0;
		int lastTierIndex = treeTiers.size() - 1;
		StringBuilder builder = new StringBuilder();
		for(List<BTreeProxy.NodeProxy> tier : treeTiers) {
			int offset = lastTierIndex - tierIndex;
			append(builder, "    ", offset);
			BTreeProxy.NodeProxy parent = null;
			for(BTreeProxy.NodeProxy node : tier) {
				if(parent != null) {
					if(parent.equals(node.getParent()))
						builder.append(" ");
					else
						builder.append(" - ");
				}
				builder.append(nodeToString(node));
				parent = node.getParent();
			}
			++ tierIndex;
			builder.append("\n");
		}
		return builder.toString();
	}
	
	private void append(StringBuilder builder, String str, int count) {
		for(int i = 0 ; i < count ; ++i)
			builder.append(str);
	}
	
	private void visitNode(
			BTreeProxy.NodeProxy node, 
			List<List<BTreeProxy.NodeProxy>> treeTiers, int index) {
		List<NodeProxy> children = node.getChildren();
		if(children.isEmpty())
			return;
		
		List<BTreeProxy.NodeProxy> current = null;
		if(treeTiers.size() > index)
			current = treeTiers.get(index);
		else 
			treeTiers.add((current = new ArrayList<>()));
		
		current.addAll(children);
		
		for(BTreeProxy.NodeProxy child : children)
			visitNode(child, treeTiers, index + 1);
	}
	
	private String nodeToString(BTreeProxy.NodeProxy node) {
		NodePrinter printer = new NodePrinter();
		printer.visit(node);
		return printer.print();
	}
	
	public static class NodePrinter implements BTreeVisitor.NodeVisitor {

		protected NodeProxy node;
		
		@Override
		public void visit(NodeProxy node) {
			this.node = node;
		}
		
		public String print() {
			StringBuilder builder = new StringBuilder();
			int entryCount = node.getEntryCount();
			if(entryCount <= 0) {
				builder.append("(" + node.node.id + ")");
			}
			for(int i = 0 ; i < entryCount ; ++i) {
				builder.append(node.getEntry(i) + "(" + node.node.id + ")");
				if(i < entryCount - 1)
					builder.append(",");
			}
			return builder.toString();
		}
	}
}

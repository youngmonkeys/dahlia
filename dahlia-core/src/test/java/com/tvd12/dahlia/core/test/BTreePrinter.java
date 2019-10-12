package com.tvd12.dahlia.core.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tvd12.dahlia.core.btree.BTreeProxy;
import com.tvd12.dahlia.core.btree.BTreeVisitor;

public class BTreePrinter implements BTreeVisitor {

	protected StringBuilder builder;
	
	@Override
	public void visit(BTreeProxy tree) {
		BTreeProxy.NodeProxy root = tree.getRoot();
		if(root == null)
			return;
		List<List<BTreeProxy.NodeProxy>> all = new ArrayList<>();
		all.add(Arrays.asList(root));
		toString(root, all, 1);
		builder = new StringBuilder();
		for(List<BTreeProxy.NodeProxy> list : all) {
			for(BTreeProxy.NodeProxy node : list)
				builder.append(node).append(" " );
			builder.append("\n");
		}
	}
	
	public String print() {
		if(builder == null)
			return "";
		return builder.toString();
	}
	
	private void toString(
			BTreeProxy.NodeProxy node, 
			List<List<BTreeProxy.NodeProxy>> all, int index) {
		List<BTreeProxy.NodeProxy> list = new ArrayList<>();
		int childCount = node.getChildCount();
		for(int i = 0 ; i < childCount ; ++i) {
			BTreeProxy.NodeProxy child = node.getChildren(i);
			if(child != null)
				list.add(child);
		}
		if(list.isEmpty())
			return;
		List<BTreeProxy.NodeProxy> current = null;
		if(all.size() > index) {
			current = all.get(index);
		}
		else {
			current = new ArrayList<>();
			all.add(current);
		}
		current.addAll(list);
		for(BTreeProxy.NodeProxy child : list)
			toString(child, all, index + 1);
	}
}

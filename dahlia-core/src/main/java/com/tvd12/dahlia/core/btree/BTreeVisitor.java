package com.tvd12.dahlia.core.btree;

public interface BTreeVisitor {

	<K, V> void visit(BTreeProxy<K, V> tree);
	
	interface NodeVisitor{
		
		<K, V> void visit(BTreeProxy.NodeProxy<K, V> node);
		
	}
	
}

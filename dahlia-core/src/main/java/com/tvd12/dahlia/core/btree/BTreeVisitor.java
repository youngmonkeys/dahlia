package com.tvd12.dahlia.core.btree;

public interface BTreeVisitor {

	void visit(BTreeProxy tree);
	
	interface NodeVisitor{
		
		void visit(BTreeProxy.NodeProxy node);
		
	}
	
}

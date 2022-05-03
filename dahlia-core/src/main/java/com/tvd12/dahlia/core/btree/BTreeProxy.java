package com.tvd12.dahlia.core.btree;

import com.tvd12.dahlia.core.btree.BTree.Node;
import com.tvd12.dahlia.core.tree.Tree;
import com.tvd12.ezyfox.util.EzyEquals;

import java.util.ArrayList;
import java.util.List;

public class BTreeProxy<K, V> {

    protected final BTree<K, V> tree;

    public BTreeProxy(BTree<K, V> tree) {
        this.tree = tree;
    }

    public NodeProxy<K, V> getRoot() {
        if (tree.root == null) {
            return null;
        }
        return new NodeProxy<>(tree.root);
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

    public static class NodeProxy<K, V> {

        protected final Node<K, V> node;

        public NodeProxy(Node<K, V> node) {
            this.node = node;
        }

        public NodeProxy<K, V> getParent() {
            if (node.parent == null) {
                return null;
            }
            return new NodeProxy<>(node.parent);
        }

        public int getEntryCount() {
            return node.entryCount;
        }

        public List<NodeProxy<K, V>> getChildren() {
            List<NodeProxy<K, V>> children = new ArrayList<>();
            for (int i = 0; ; ++i) {
                Node<K, V> child = node.children[i];
                if (child == null) {
                    break;
                }
                if (i > node.entryCount) {
                    break;
                }
                children.add(new NodeProxy<>(child));
            }
            return children;
        }

        public Tree.Entry<K, V> getEntry(int index) {
            if (node.entries[index] == null) {
                return null;
            }
            return node.entries[index];
        }

        @Override
        public boolean equals(Object obj) {
            return new EzyEquals<NodeProxy<K, V>>()
                .function(t -> t.node)
                .isEquals(this, obj);
        }
    }
}

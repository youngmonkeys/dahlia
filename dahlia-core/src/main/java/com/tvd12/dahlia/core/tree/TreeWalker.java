package com.tvd12.dahlia.core.tree;

public interface TreeWalker<K, V> {

    void accept(Tree.Entry<K, V> entry);

    default boolean next() {
        return true;
    }
}

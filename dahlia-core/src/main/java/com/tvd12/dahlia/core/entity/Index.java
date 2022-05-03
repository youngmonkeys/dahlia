package com.tvd12.dahlia.core.entity;

import com.tvd12.dahlia.core.btree.BTree;
import com.tvd12.dahlia.core.function.IdConsumer;
import com.tvd12.dahlia.core.setting.IndexSetting;
import com.tvd12.dahlia.core.tree.Tree;
import com.tvd12.dahlia.core.tree.Tree.Entry;
import com.tvd12.dahlia.core.tree.TreeWalker;
import com.tvd12.ezyfox.entity.EzyObject;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Index {

    @Getter
    protected final IndexSetting setting;
    protected final Tree<Comparable, Comparable> tree;

    public Index(IndexSetting setting) {
        this.setting = setting;
        this.tree = new BTree<>(newKeyComparator(setting.getFields()));
    }

    public static Index ofId() {
        return new Index(IndexSetting.ID_SETTING);
    }

    public Comparable find(Comparable key) {
        return tree.get(key);
    }

    public void insert(Comparable key, Comparable id) {
        this.tree.insert(key, id);
    }

    public Comparable remove(Comparable key) {
        return tree.remove(key);
    }

    public void forEach(IdConsumer consumer) {
        this.tree.walk(new TreeWalker<Comparable, Comparable>() {
            @Override
            public void accept(Entry<Comparable, Comparable> entry) {
                consumer.accept(entry.getValue());
            }

            @Override
            public boolean next() {
                return consumer.next();
            }
        });
    }

    protected Comparator<Comparable> newKeyComparator(Map<String, Boolean> fields) {
        if (fields.size() == 1) {
            for (Map.Entry<String, Boolean> e : fields.entrySet()) {
                Boolean asc = e.getValue();
                if (asc) {
                    return Comparable::compareTo;
                } else {
                    return Comparator.reverseOrder();
                }
            }
        }
        List<Comparator<EzyObject>> comparators = new ArrayList<>();
        for (Map.Entry<String, Boolean> e : fields.entrySet()) {
            Comparator<EzyObject> comparator;
            String field = e.getKey();
            Boolean asc = e.getValue();
            if (asc) {
                comparator = (a, b) -> {
                    Comparable v1 = a.get(field);
                    Comparable v2 = b.get(field);
                    return v1.compareTo(v2);
                };
            } else {
                comparator = (a, b) -> {
                    Comparable v1 = a.get(field);
                    Comparable v2 = b.get(field);
                    return v2.compareTo(v1);
                };
            }
            comparators.add(comparator);
        }
        return (a, b) -> {
            for (Comparator<EzyObject> comparator : comparators) {
                int result = comparator.compare((EzyObject) a, (EzyObject) b);
                if (result != 0) {
                    return result;
                }
            }
            return 0;
        };
    }
}

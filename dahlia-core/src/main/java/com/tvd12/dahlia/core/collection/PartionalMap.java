package com.tvd12.dahlia.core.collection;

import com.tvd12.dahlia.core.util.MapPartitions;

import java.util.*;

@SuppressWarnings({"rawtypes", "unchecked"})
public class PartionalMap<K, V> implements Map<K, V> {

    protected final int maxPartition;
    protected final EntityMapPartition<K, V>[] partitions;

    public PartionalMap() {
        this(32);
    }

    public PartionalMap(int maxPartition) {
        this.maxPartition = maxPartition;
        this.partitions = newPartitions();
    }

    protected EntityMapPartition<K, V>[] newPartitions() {
        EntityMapPartition<K, V>[] array = new EntityMapPartition[maxPartition];
        for (int i = 0; i < array.length; ++i) {
            array[i] = newPartition();
        }
        return array;
    }

    protected EntityMapPartition<K, V> newPartition() {
        return new EntityMapPartition<>();
    }

    @Override
    public V put(K key, V value) {
        int pindex = MapPartitions.getPartitionIndex(maxPartition, key);
        return partitions[pindex].put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        Map<Integer, Map> cm = MapPartitions.classifyMaps(maxPartition, (Map) m);
        for (Integer index : cm.keySet()) {
            partitions[index].putAll(cm.get(index));
        }
    }

    @Override
    public V get(Object key) {
        int pindex = MapPartitions.getPartitionIndex(maxPartition, key);
        return partitions[pindex].get(key);
    }

    @Override
    public boolean containsKey(Object key) {
        int pindex = MapPartitions.getPartitionIndex(maxPartition, key);
        return partitions[pindex].containsKey(key);
    }

    @Override
    public V remove(Object key) {
        int pindex = MapPartitions.getPartitionIndex(maxPartition, key);
        return partitions[pindex].remove(key);
    }


    @Override
    public void clear() {
        for (int i = 0; i < maxPartition; ++i) {
            partitions[i].clear();
        }
    }

    @Override
    public int size() {
        return (int) sizeLong();
    }

    public long sizeLong() {
        long size = 0;
        for (int i = 0; i < maxPartition; ++i) {
            size += partitions[i].size();
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return sizeLong() > 0;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < maxPartition; ++i) {
            if (partitions[i].containsValue(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<K> keySet() {
        Set<K> answer = new HashSet<>();
        for (int i = 0; i < maxPartition; ++i) {
            answer.addAll(partitions[i].keySet());
        }
        return answer;
    }

    @Override
    public Collection<V> values() {
        List<V> answer = new ArrayList<>();
        for (int i = 0; i < maxPartition; ++i) {
            answer.addAll(partitions[i].values());
        }
        return answer;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> answer = new HashSet<>();
        for (int i = 0; i < maxPartition; ++i) {
            answer.addAll(partitions[i].entrySet());
        }
        return answer;
    }


    private static class EntityMapPartition<K, V> {

        protected final Map<K, V> map;

        public EntityMapPartition() {
            this.map = new HashMap<>();
        }

        public V put(K key, V value) {
            return map.put(key, value);
        }

        public void putAll(Map<K, V> m) {
            map.putAll(m);
        }

        public V get(Object key) {
            //noinspection SuspiciousMethodCalls
            return map.get(key);
        }

        public boolean containsKey(Object key) {
            //noinspection SuspiciousMethodCalls
            return map.containsKey(key);
        }

        public boolean containsValue(Object value) {
            //noinspection SuspiciousMethodCalls
            return map.containsValue(value);
        }

        public V remove(Object key) {
            //noinspection SuspiciousMethodCalls
            return map.remove(key);
        }

        public int size() {
            return map.size();
        }

        public void clear() {
            map.clear();
        }

        public Set<K> keySet() {
            return map.keySet();
        }

        public Collection<? extends V> values() {
            return map.values();
        }

        public Collection<? extends Entry<K, V>> entrySet() {
            return map.entrySet();
        }
    }
}

package edu.sjsu.cmpe.cache.repository;

import edu.sjsu.cmpe.cache.domain.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class ChronicleMapCache implements CacheInterface {
    /** chronicle-map cache. (Key, Value) -> (Key, Entry) */
    private final Map<Long, Entry> chronicalMap;

    public ChronicleMapCache(Map<Long, Entry> entries) {
        chronicalMap = entries;
    }

    @Override
    public Entry save(Entry newEntry) {
        checkNotNull(newEntry, "newEntry instance must not be null");
        chronicalMap.putIfAbsent(newEntry.getKey(), newEntry);

        return newEntry;
    }

    @Override
    public Entry get(Long key) {
        checkArgument(key > 0,
                "Key was %s but expected greater than zero value", key);
        return chronicalMap.get(key);
    }

    @Override
    public List<Entry> getAll() {
        return new ArrayList<Entry>(chronicalMap.values());
    }
}

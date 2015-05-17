package edu.sjsu.cmpe.cache.client;

import com.google.common.hash.Hashing;

import java.util.*;

public class ConsistentHashCall {


    public  void consistentHash(List<CacheServiceInterface> cacheList, Map<Long, String> KVpair)
    {
        ConsistentHash<CacheServiceInterface> consistentHash;

        Integer replicationFactor = 10;


        Set set = KVpair.entrySet();
        Iterator iterator = set.iterator();

        consistentHash = new ConsistentHash<CacheServiceInterface>(Hashing.md5(), replicationFactor, cacheList);

        while (iterator.hasNext()) {

            Map.Entry mapentry = (Map.Entry) iterator.next();

            CacheServiceInterface bucket = consistentHash.get(mapentry.getKey());
            bucket.put((Long) mapentry.getKey(), (String) mapentry.getValue());
            System.out.println("put(" + mapentry.getKey() + " => " + mapentry.getValue() + ")");

        }
        for (int key = 1; key <= 10; key++) {
            CacheServiceInterface bucket = consistentHash.get(key);
            String value = bucket.get(key);
            System.out.println("get(" + key + ") => " + value);
        }
        System.out.println("\nValue Distribution: \n" +
                        "Server A => http://localhost:3000/cache/  =>\n" + cacheList.get(0).getAllValues() +
                        "\nServer B => http://localhost:3001/cache/  =>\n" + cacheList.get(1).getAllValues() +
                        "\nServer C => http://localhost:3002/cache/  =>\n" + cacheList.get(2).getAllValues()
        );


    }
}

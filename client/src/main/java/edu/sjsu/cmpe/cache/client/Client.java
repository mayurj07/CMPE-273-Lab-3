package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {

    public static  List<CacheServiceInterface> cacheList;
    public static Map<Long, String> KVpair = new HashMap<Long, String>();

    public static ConsistentHashCall ch = new ConsistentHashCall();
    public static HRWCall hrw = new HRWCall();

    public static void main(String[] args) throws Exception
    {
        System.out.println("\nStarting Cache Client...\n");

        cacheList = new ArrayList<CacheServiceInterface>();

        cacheList.add(new DistributedCacheService("http://localhost:3000"));
        cacheList.add(new DistributedCacheService("http://localhost:3001"));
        cacheList.add(new DistributedCacheService("http://localhost:3002"));

        KVpair.put(new Long(1), "a");
        KVpair.put(new Long(2), "b");
        KVpair.put(new Long(3), "c");
        KVpair.put(new Long(4), "d");
        KVpair.put(new Long(5), "e");
        KVpair.put(new Long(6), "f");
        KVpair.put(new Long(7), "g");
        KVpair.put(new Long(8), "h");
        KVpair.put(new Long(9), "i");
        KVpair.put(new Long(10), "j");

        ch.consistentHash(cacheList, KVpair);

        //hrw.rendezvousHash(cacheList, KVpair);

    }




}
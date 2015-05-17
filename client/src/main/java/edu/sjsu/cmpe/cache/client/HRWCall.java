package edu.sjsu.cmpe.cache.client;

import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HRWCall {


    public void rendezvousHash(List<CacheServiceInterface> cacheList, Map<Long, String> KVpair) {

        Funnel<CharSequence> strFunnel = Funnels.stringFunnel(Charset.defaultCharset());
        Funnel<Long> longFunnel = Funnels.longFunnel();

        ArrayList<String> cacheUrls = new ArrayList<String>();
        for(CacheServiceInterface c : cacheList) {
            DistributedCacheService d = (DistributedCacheService)c;
            cacheUrls.add(d.getUrl());
        }

        RendezvousHash rendezvousHash = new RendezvousHash(Hashing.md5(),longFunnel,strFunnel,cacheUrls);

        for(long key = 1 ; key <= 10 ; key++)
        {
            String value = Character.toString((char)('a' + key - 1));
            // returns url of cache
            String cacheUrl = (String)rendezvousHash.get(key);
            cacheList.get(cacheUrls.indexOf(cacheUrl)).put(key,value);
            //int bucket = ;
            System.out.println("put(" + key + " => " + value + ") ==> routed to :" + rendezvousHash.get(key));
        }

        for(long key = 1; key <= 10 ; key++)
        {
            String cacheUrl = (String)rendezvousHash.get(key);
            System.out.println("get(" + key + ") => " + cacheList.get(cacheUrls.indexOf(cacheUrl)).get(key));
        }

        System.out.println("\nValue Distribution: \n" +
                        "Server A => http://localhost:3000/cache/  =>\n" + cacheList.get(0).getAllValues() +
                        "\nServer B => http://localhost:3001/cache/  =>\n" + cacheList.get(1).getAllValues() +
                        "\nServer C => http://localhost:3002/cache/  =>\n" + cacheList.get(2).getAllValues()
        );
    }
}

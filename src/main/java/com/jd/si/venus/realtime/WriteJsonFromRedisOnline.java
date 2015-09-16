/*
package com.jd.si.venus.realtime;

import com.google.gson.Gson;
import com.jd.jim.cli.Cluster;
import com.jd.si.jupiter.db.JimCacheCloud;
import com.jd.si.jupiter.soa.Serialization.ThriftSerialization;
import com.jd.si.venus.base.thrift.FeatureMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

*/
/**
 * Created by TanZhen on 2015/8/10.
 *//*


public class WriteJsonFromRedisOnline {

    public static void main(String[] args) throws Exception {

    */
/*    File f = new File("d:\\feature_meta.json");  //length= 67
        Long fileLens = f.length();
        byte[] content = new byte[fileLens.intValue()];

        FileInputStream fs = new FileInputStream(f);
        fs.read(content);
        fs.close();
        String s = new String(content,"UTF-8");
        System.out.println(s);

        Gson gson = new Gson();
        FeatureDesc featureDesc = gson.fromJson(s,FeatureDesc.class);
        System.out.println(featureDesc.attributes.size());
*//*

        Map<String,String> map = new HashMap<String,String>();
        List<String> skus = new ArrayList<String>();
        BufferedReader br  = null;
        br = new BufferedReader(new FileReader("/data0/recsys/tanzhen/skus.txt"));
        String line = null;
        while((line = br.readLine()) != null){
            skus.add(line);
        }
        Cluster jimdb =  new JimCacheCloud("/redis/cluster/1183","1433400976033").getJimClient();
        Gson gson = new Gson();
        for(String sku : skus){
            byte[] bkey = ("realtime-feature-"+sku).getBytes();
            byte[] val = jimdb.get(bkey);
            if(val != null){
                FeatureMap fm = ThriftSerialization.fromBytes(FeatureMap.class,val);
                String json = gson.toJson(fm);
                map.put(sku,json);
            }else {
                //System.out.println(sku);
            }
        }
        br.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter("/data0/recsys/tanzhen/json.txt"));
        for(Map.Entry<String,String> entry : map.entrySet()){
            bw.write(entry.getKey()+"@"+entry.getValue()+"\n");
        }
        System.out.println(map.size());
        bw.flush();
        bw.close();




    }

}*/

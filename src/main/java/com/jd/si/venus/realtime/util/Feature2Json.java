package com.jd.si.venus.realtime.util;

import com.google.gson.Gson;
import com.jd.si.venus.realtime.entity.Attribute;
import com.jd.si.venus.realtime.entity.FeatureDesc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TanZhen on 2015/8/30.
 */
public class Feature2Json {
    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();

        BufferedReader br = new BufferedReader(new FileReader("d:\\col.txt"));
        String line = null;
        List<Attribute> list = new ArrayList<Attribute>();
        while((line = br.readLine()) != null){
            String[] seg = line.split(" ");
            String name = seg[0];
            Attribute attribute = new Attribute(name,true);
            list.add(attribute);
        }
        FeatureDesc desc = new FeatureDesc(list);
        System.out.println(desc.attributes.size());
        br.close();
        String json = gson.toJson(desc);
        System.out.println(json);
    }
}

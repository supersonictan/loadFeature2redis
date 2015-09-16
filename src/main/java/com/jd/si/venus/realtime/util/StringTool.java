package com.jd.si.venus.realtime.util;

import com.google.gson.Gson;
import com.jd.si.jupiter.soa.Serialization.ThriftSerialization;
import com.jd.si.venus.base.thrift.Feature;
import com.jd.si.venus.base.thrift.FeatureMap;
import com.jd.si.venus.base.thrift.FeatureType;
import com.jd.si.venus.realtime.entity.Attribute;
import com.jd.si.venus.realtime.entity.FeatureDesc;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TanZhen on 2015/8/28.
 */
public class StringTool {
    private static final Log logger = LogFactory.getLog(StringTool.class);


    public static byte[] String2ByteArr(String text,List<Attribute> attributes){

        FeatureMap featureMap = new FeatureMap();
        String[] seg = text.split(SystemConfig.SEPARATOR);
        String caseId = seg[0].trim();
        Map<String,Feature> map = new HashMap<String,Feature>();
        for(int i=1; i<seg.length; i++){
            int reflectToFeatureId = i-1;
            String featureId = attributes.get(reflectToFeatureId).name;  // not 100% sure
            String val = seg[i].trim();
            FeatureType type = attributes.get(reflectToFeatureId).isCont ? FeatureType.CONTINOUS : FeatureType.DISCRETE;
            Feature f = new Feature(featureId,val,type);
            map.put(featureId,f);
        }

        featureMap.setCaseId(caseId);
        featureMap.setFeatureMap(map);
        byte[] bytes = ThriftSerialization.toCompactBytes(featureMap);
        return bytes;
    }
    /*public static byte[] String2ByteArr(String text,String s){
        Gson gson = new Gson();
        featureDesc = gson.fromJson(s,FeatureDesc.class);
        attributes = featureDesc.attributes;
        System.out.println(attributes.size());
        logger.error(attributes.size());
        FeatureMap featureMap = new FeatureMap();
        String[] seg = text.split(SystemConfig.SEPARATOR);
        String caseId = seg[0].trim();
        Map<String,Feature> map = new HashMap<String,Feature>();
        for(int i=1; i<seg.length; i++){
            *//*String featureId = attributes.get(i).name;
            String val = seg[i].trim();
            FeatureType type = attributes.get(i).isCont ? FeatureType.CONTINOUS : FeatureType.DISCRETE;
            Feature f = new Feature(featureId,val,type);
            map.put(featureId,f);*//*
            int reflectToFeatureId = i-1;
            String featureId = attributes.get(reflectToFeatureId).name;  // not 100% sure
            String val = seg[i].trim();
            FeatureType type = attributes.get(reflectToFeatureId).isCont ? FeatureType.CONTINOUS : FeatureType.DISCRETE;
            Feature f = new Feature(featureId,val,type);
            map.put(featureId,f);
        }

        featureMap.setCaseId(caseId);
        featureMap.setFeatureMap(map);
        byte[] bytes = ThriftSerialization.toCompactBytes(featureMap);
        return bytes;
    }*/

    public static FeatureMap toFeatureObj(String text,List<Attribute> attributes){
        FeatureMap featureMap = new FeatureMap();
        String[] seg = text.split(SystemConfig.SEPARATOR);
        String caseId = seg[0].trim();
        Map<String,Feature> map = new HashMap<String,Feature>();
        for(int i=1; i<seg.length; i++){ //cause i=0 is sku
            int reflectToFeatureId = i-1;
            String featureId = attributes.get(reflectToFeatureId).name;  // not 100% sure
            String val = seg[i].trim();
            FeatureType type = attributes.get(reflectToFeatureId).isCont ? FeatureType.CONTINOUS : FeatureType.DISCRETE;
            Feature f = new Feature(featureId,val,type);
            map.put(featureId,f);
        }

        featureMap.setCaseId(caseId);
        featureMap.setFeatureMap(map);

        return featureMap;
    }
    /*public static FeatureMap toFeatureObj(String text,String s){
        Gson gson = new Gson();
        featureDesc = gson.fromJson(s,FeatureDesc.class);
        attributes = featureDesc.attributes;
        System.out.println(attributes.size());
        FeatureMap featureMap = new FeatureMap();
        String[] seg = text.split(SystemConfig.SEPARATOR);
        String caseId = seg[0].trim();
        Map<String,Feature> map = new HashMap<String,Feature>();
        for(int i=1; i<seg.length; i++){ //cause i=0 is sku
            int reflectToFeatureId = i-1;
            String featureId = attributes.get(reflectToFeatureId).name;  // not 100% sure
            String val = seg[i].trim();
            FeatureType type = attributes.get(reflectToFeatureId).isCont ? FeatureType.CONTINOUS : FeatureType.DISCRETE;
            Feature f = new Feature(featureId,val,type);
            map.put(featureId,f);
        }

        featureMap.setCaseId(caseId);
        featureMap.setFeatureMap(map);

        return featureMap;
    }*/

   /* public static FeatureMap String2JSONStr(String text,String s){
        Gson gson = new Gson();
        featureDesc = gson.fromJson(s,FeatureDesc.class);
        attributes = featureDesc.attributes;
        System.out.println(attributes.size());
        logger.error(attributes.size());
        FeatureMap featureMap = new FeatureMap();
        String[] seg = text.split(SystemConfig.SEPARATOR);
        String caseId = seg[0].trim();
        Map<String,Feature> map = new HashMap<String,Feature>();
        for(int i=1; i<seg.length; i++){
            String featureId = attributes.get(i).name;
            String val = seg[i].trim();
            FeatureType type = attributes.get(i).isCont ? FeatureType.CONTINOUS : FeatureType.DISCRETE;
            Feature f = new Feature(featureId,val,type);
            map.put(featureId,f);
        }

        featureMap.setCaseId(caseId);
        featureMap.setFeatureMap(map);

        return featureMap;
    }
*/
    public static String getKey(String str){
        return str.split(CommonConstants.SEPARATOR)[0].trim();
    }
}

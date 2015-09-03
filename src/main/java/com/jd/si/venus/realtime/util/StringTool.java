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
    public static FeatureDesc featureDesc;
    public static List<Attribute> attributes;

    public static byte[] String2ByteArr(String text,String s){
        Gson gson = new Gson();
        featureDesc = gson.fromJson(s,FeatureDesc.class);
        attributes = featureDesc.attributes;
        System.out.println(attributes.size());
        logger.error(attributes.size());
        FeatureMap featureMap = new FeatureMap();
        String[] seg = text.split(SystemConfig.SEPARATOR);
        String caseId = seg[0];
        Map<String,Feature> map = new HashMap<String,Feature>();
        for(int i=1; i<seg.length; i++){
            String featureId = attributes.get(i).name;
            String val = seg[i];
            FeatureType type = attributes.get(i).isCont ? FeatureType.CONTINOUS : FeatureType.DISCRETE;
            Feature f = new Feature(featureId,val,type);
            map.put(featureId,f);
        }

        featureMap.setCaseId(caseId);
        featureMap.setFeatureMap(map);
        byte[] bytes = ThriftSerialization.toBytes(featureMap);
        return bytes;
    }

    public static String getKey(String str){
        return str.split(CommonConstants.SEPARATOR)[0];
    }
}

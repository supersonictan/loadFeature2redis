package com.jd.si.venus.realtime.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by 1 on 2015/8/28.
 */
public class CommonConstants {
    public static final String zk_Path;
    public static final String model_namespace;
    public static final int zk_timeout;
    public static final String SEPARATOR ;

    static {
        InputStream in = null;
        Properties properties = null;
        try{
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream("conf.properties");
            properties = new Properties();
            properties.load(in);
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                if(in != null){
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        zk_Path = properties.getProperty("zk_Path");
        zk_timeout = Integer.parseInt(properties.getProperty("zk_timeout"));
        model_namespace = properties.getProperty("model_namespace");
        SEPARATOR = properties.getProperty("SEPARATOR");
    }
}

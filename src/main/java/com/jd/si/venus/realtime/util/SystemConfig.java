package com.jd.si.venus.realtime.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by TanZhen on 2015/8/7.
 */
public class SystemConfig {
    private static final Log logger = LogFactory.getLog(SystemConfig.class);
    public static final String zk_Path;
    public static final String model_namespace;
    public static final int zk_timeout;
    public static final String token;
    public static final String SEPARATOR ;
    //temp
    public static final String cloudConfigId ;
    public static final String cloudToken ;
    public static final String jmToken ;


    static {
        InputStream in = null;
        Properties properties = null;
        try{
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream("conf.properties");
            properties = new Properties();
            properties.load(in);
        }catch (IOException e){
            logger.error("properties is not found!",e);
        } finally {
            try{
                if(in != null){
                    in.close();
                }
            }catch (IOException e){
                logger.error("load properties failed",e);
            }
        }
        zk_Path = properties.getProperty("zk_Path");
        zk_timeout = Integer.parseInt(properties.getProperty("zk_timeout"));
        model_namespace = properties.getProperty("model_namespace");
        token = properties.getProperty("token");
        SEPARATOR = properties.getProperty("SEPARATOR");
        cloudConfigId = properties.getProperty("cloudConfigId");
        cloudToken = properties.getProperty("cloudToken");
        jmToken = properties.getProperty("jmToken");
    }
}
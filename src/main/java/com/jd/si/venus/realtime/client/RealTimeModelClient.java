package com.jd.si.venus.realtime.client;

import com.jd.si.jupiter.soa.RPCClientBuilder;
import com.jd.si.venus.base.thrift.RealTimeModelServer;
import com.jd.si.venus.base.thrift.ResourceType;
import com.jd.si.venus.realtime.util.SystemConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;

/**
 * Created by TanZhen on 2015/8/10.
 */
public class RealTimeModelClient {
    private static Log logger = LogFactory.getLog(RealTimeModelClient.class);
    private static RealTimeModelServer.Iface client = null;

    private static RealTimeModelServer.Iface getRealTimeModelClient(){
        logger.info("init RealTimeModelClient.....");
        RealTimeModelServer.Iface client = null;
        try {
            client = new RPCClientBuilder<RealTimeModelServer.Iface>()
                    .setClientClass(RealTimeModelServer.Client.class)
                    .setZookeepers(SystemConfig.zk_Path)
                    .setNamespace(SystemConfig.model_namespace)
                    .setUseNameSpace(true)
                    .setTimeout(SystemConfig.zk_timeout)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error init RealtimeModelClient",e);
        }
        return client;
    }

    public static RealTimeModelServer.Iface getClient(){
        if(client == null){
            synchronized ( RealTimeModelClient.class){
                client = getRealTimeModelClient();
            }
        }
        return client;
    }

    public static void main(String[] args) throws TException {
        System.out.println(RealTimeModelClient.getClient().getRealTimeModel(ResourceType.CACHE, "res-1438839868623", "123"));
    }
}

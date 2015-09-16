package com.jd.si.venus.realtime;


import com.google.gson.Gson;
import com.jd.jim.cli.Cluster;
import com.jd.si.jupiter.db.JimCacheCloud;
import com.jd.si.jupiter.soa.Serialization.ThriftSerialization;
import com.jd.si.venus.base.thrift.FeatureMap;
import com.jd.si.venus.base.thrift.RealTimeModel;
import com.jd.si.venus.base.thrift.RealTimeModelServer;
import com.jd.si.venus.base.thrift.ResourceType;
import com.jd.si.venus.feature.client.FeatureClient;
import com.jd.si.venus.realtime.client.RealTimeModelClient;
import com.jd.si.venus.realtime.entity.Attribute;
import com.jd.si.venus.realtime.entity.FeatureDesc;
import com.jd.si.venus.realtime.util.StringTool;
import com.jd.si.venus.realtime.util.SystemConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.thrift.TException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tan on 2015/8/29.
 */
public class WriteReducer extends Reducer<Text, Text, NullWritable, NullWritable> {
    private static final Log logger = LogFactory.getLog(WriteReducer.class);
    static String  ZK_PATH = "172.17.22.13:2181,172.17.22.14:2181,172.17.22.15:2181";
    static String FEATURE_NAMESPACE = "/si/venus/rtfeature";
    static int ZK_TIMEOUT = 3000;
    private static FeatureClient client = new FeatureClient(ZK_PATH,FEATURE_NAMESPACE,ZK_TIMEOUT);
    private String json = null;
    public FeatureDesc featureDesc;
    public List<Attribute> attributes;
    Counter read,send;
    Cluster jimdb =  new JimCacheCloud(SystemConfig.cloudConfigId,SystemConfig.jmToken).getJimClient();


    @Override
    public void setup(Context context){
        json = context.getConfiguration().get("json");
        Gson gson = new Gson();
        featureDesc = gson.fromJson(json,FeatureDesc.class);
        attributes = featureDesc.attributes;
    }

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context){
        read = context.getCounter(BulkPush.counterEnum.Reduce_rev);
        read.increment(1);
        byte[] objByte = null;

        String valWithSKU = values.iterator().next().toString();
        String caseId = "realtime-feature-"+key.toString();
        objByte = StringTool.String2ByteArr(valWithSKU,attributes);

        /*FeatureMap fm  = StringTool.toFeatureObj(valWithSKU,attributes);
        client.setFeature(SystemConfig.cloudToken,caseId,fm,ResourceType.CACHE);*/

        jimdb.setEx(caseId.getBytes(),objByte,7, TimeUnit.DAYS);

        send = context.getCounter(BulkPush.counterEnum.Reduce_send);
        send.increment(1);
    }
}

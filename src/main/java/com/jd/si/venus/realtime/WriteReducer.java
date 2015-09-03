package com.jd.si.venus.realtime;

import com.jd.si.jupiter.soa.Serialization.ThriftSerialization;
import com.jd.si.venus.base.thrift.FeatureMap;
import com.jd.si.venus.base.thrift.RealTimeModel;
import com.jd.si.venus.base.thrift.RealTimeModelServer;
import com.jd.si.venus.base.thrift.ResourceType;
import com.jd.si.venus.realtime.client.RealTimeModelClient;
import com.jd.si.venus.realtime.util.SystemConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.thrift.TException;

import java.io.IOException;

/**
 * Created by Tan on 2015/8/29.
 */
public class WriteReducer extends Reducer<LongWritable, BytesWritable, LongWritable, Text> {
    private static final Log logger = LogFactory.getLog(WriteReducer.class);
    @Override
    public void setup(Context context){

    }

    @Override
    public void reduce(LongWritable key, Iterable<BytesWritable> values, Context context){
        /*byte[] bytes = values.iterator().next().getBytes();
        RealTimeModelServer.Iface client = RealTimeModelClient.getClient();
        RealTimeModel realTimeModel = new RealTimeModel();
        realTimeModel.setModel(bytes);
        try {
            client.setRealTimeModel(ResourceType.CACHE,SystemConfig.token,key.toString(),realTimeModel);
        } catch (TException e) {
            e.printStackTrace();
        }*/

        FeatureMap ss = ThriftSerialization.fromBytes(FeatureMap.class, values.iterator().next().getBytes());
        String s = ss.toString();
        try {
            context.write(key,new Text(s));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

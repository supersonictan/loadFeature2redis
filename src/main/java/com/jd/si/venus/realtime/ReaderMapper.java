package com.jd.si.venus.realtime;

import com.jd.si.venus.base.thrift.FeatureMap;
import com.jd.si.venus.realtime.util.CommonConstants;
import com.jd.si.venus.realtime.util.StringTool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanzhen on 2015/8/28.
 */
public class ReaderMapper extends Mapper<LongWritable,Text,Text,Text> {
    private static final Log logger = LogFactory.getLog(ReaderMapper.class);
    Counter read,send;

    @Override
    protected void setup(Context context){

    }
    @Override
    protected void map(LongWritable key, Text value, Context context){
        /**
         * output: skuId : Text with skuid
         */
        read = context.getCounter(BulkPush.counterEnum.Mapper_read);
        read.increment(1);
        String skuId = value.toString().split(CommonConstants.SEPARATOR)[0].trim();
        try {
            context.write(new Text(skuId), value);
            send = context.getCounter(BulkPush.counterEnum.Mapper_send);
            send.increment(1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

package com.jd.si.venus.realtime;

import com.jd.si.venus.realtime.util.StringTool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2015/8/28.
 */
public class ReaderMapper extends Mapper<LongWritable,Text,LongWritable,BytesWritable> {
    private static final Log logger = LogFactory.getLog(ReaderMapper.class);
    @Override
    protected void setup(Context context){

    }
    @Override
    protected void map(LongWritable key, Text value, Context context){
        /**
         * output: skuId : thrift_byte[]
         */
        String json = context.getConfiguration().get("json");
        String[] skuStr = context.getConfiguration().get("skus").split(";");
        List<String> skuList = new ArrayList<String>();
        for(String s : skuStr){
            skuList.add(s);
        }


        if(value != null && !"".equals(value.toString())){
            String skuId = StringTool.getKey(value.toString());
            if(skuList.contains(skuId)){
                byte[] val = StringTool.String2ByteArr(value.toString(),json);
                try {
                    //context.write(new Text(skuId),new BytesWritable(val));
                    context.write(new LongWritable(Long.parseLong(skuId)),new BytesWritable(val));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

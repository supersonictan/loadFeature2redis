package com.jd.si.venus.realtime;

import com.google.gson.Gson;
import com.jd.si.venus.realtime.entity.FeatureDesc;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Tanzhen on 2015/8/29.
 */
public class BulkPush implements Tool {
    private static final Log logger = LogFactory.getLog(BulkPush.class);
    Configuration configuration;

    public static void main(String[] args){
        try {
            String[] s = {"s"};
            int status = ToolRunner.run(new BulkPush(),s);
            System.exit(status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int run(String[] strings) throws Exception {
        Job job = Job.getInstance(getConf(), "haha");
        job.setJarByClass(BulkPush.class);

        job.setMapOutputKeyClass(LongWritable.class); //map的Key类型
        job.setMapOutputValueClass(BytesWritable.class);

        job.setMapperClass(ReaderMapper.class);
        job.setReducerClass(WriteReducer.class);

        job.setNumReduceTasks(2);

        //FileInputFormat.addInputPath(job, new Path("xiajun/data.txt"));//设置输入文件路径
        FileInputFormat.addInputPath(job, new Path("/user/recsys/tmp.db/tmp_l2r_sku_feature_stat_7d_tb_new_2/dt=2015-08-28/000111_0"));//设置输入文件路径
        FileOutputFormat.setOutputPath(job, new Path("tanzhen/"));//设置输出文件路径

        boolean successed = job.waitForCompletion(true);
        int i = successed ? 0 : 1;
        return i;
    }

    @Override
    public void setConf(Configuration configuration) {
        this.configuration = new Configuration();
    }

    @Override
    public Configuration getConf() {
        File f = new File("/data0/recsys/tanzhen/load2Redis/feature_meta.json");
        Long fileLens = f.length();
        byte[] content = new byte[fileLens.intValue()];
        String s = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            fs.read(content);
            fs.close();
            s = new String(content,"UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Configuration conf = new Configuration();
        conf.set("json",s);
        return conf;
    }
}

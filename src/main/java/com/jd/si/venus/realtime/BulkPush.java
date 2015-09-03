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

import java.io.*;

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

        job.setNumReduceTasks(8);

        //FileInputFormat.addInputPath(job, new Path("xiajun/data.txt"));//设置输入文件路径
        FileInputFormat.addInputPath(job, new Path("/user/recsys/tmp.db/tmp_l2r_sku_feature_stat_7d_tb_new_2/dt=2015-08-29"));//设置输入文件路径
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

        String desc = getFeatureDesc("/data0/recsys/tanzhen/load2Redis/feature_meta.json");
        String skus = getLoadSkus("/data0/recsys/tanzhen/skus.txt");

        Configuration conf = new Configuration();
        conf.set("json",desc);
        conf.set("skus",skus);
        return conf;
    }
    public String getFeatureDesc(String path){
        File f = new File(path);
        Long fileLens = f.length();
        byte[] content = new byte[fileLens.intValue()];
        String s = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            fs.read(content);

            s = new String(content,"UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return s;
    }
    public String getLoadSkus(String path){
        File f = new File(path);
        StringBuffer s = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));
            String line = null;
            while((line=br.readLine()) != null){
                s.append(line+";");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return s.toString();
    }
}

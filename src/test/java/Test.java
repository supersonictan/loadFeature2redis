import com.google.gson.Gson;
import com.jd.jim.cli.Cluster;
import com.jd.si.jupiter.db.JimCacheCloud;
import com.jd.si.venus.realtime.entity.FeatureDesc;
import com.jd.si.venus.realtime.util.SystemConfig;

import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by TanZhen on 2015/8/10.
 */

public class Test {

    public static void main(String[] args) throws Exception {

    /*    File f = new File("d:\\feature_meta.json");  //length= 67
        Long fileLens = f.length();
        byte[] content = new byte[fileLens.intValue()];

        FileInputStream fs = new FileInputStream(f);
        fs.read(content);
        fs.close();
        String s = new String(content,"UTF-8");
        System.out.println(s);

        Gson gson = new Gson();
        FeatureDesc featureDesc = gson.fromJson(s,FeatureDesc.class);
        System.out.println(featureDesc.attributes.size());
*/


/*

        BufferedReader br = new BufferedReader(new FileReader("d:\\000111_0")); //length= 66 比之前的少了dt
        String line = br.readLine();

        String[] seg = line.split("\\001");
        System.out.println(seg.length);
*/

        Cluster jimdb =  new JimCacheCloud("/redis/cluster/1183","1433400976033").getJimClient();

        byte[] bkey = ("realtime-test-"+123123).getBytes();
        byte[] bVal = "abcabc".getBytes();
        jimdb.setEx(bkey,bVal,7, TimeUnit.DAYS);

        byte[] val = jimdb.get(bkey);
        System.out.println(new String(val));


    }

}
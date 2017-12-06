package com.personal.oyl.code.example.storm.jdbc.spout;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

public class DbTopology {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("DBSpout", new DBSpout(), 1);
        builder.setBolt("Notice Bolt", new NoticeBolt(), 1).shuffleGrouping("DBSpout");

        Config conf = new Config();
        LocalCluster cluster = new LocalCluster();

        cluster.submitTopology("DbTopology", conf, builder.createTopology());

        Utils.sleep(10000); // sleep 10秒后kill掉
        cluster.killTopology("DbTopology");
        cluster.shutdown();
    }
}

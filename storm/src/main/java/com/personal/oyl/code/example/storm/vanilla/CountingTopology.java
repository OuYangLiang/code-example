package com.personal.oyl.code.example.storm.vanilla;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

public class CountingTopology {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("Sentence Spout", new SentenceSpout(), 1);
        builder.setBolt("Split Bolt", new SplitBolt(), 1).shuffleGrouping("Sentence Spout");
        builder.setBolt("Count Bolt", new CountBolt(), 1).fieldsGrouping("Split Bolt", new Fields("word"));
        builder.setBolt("Report Bolt", new ReportBolt(), 1).globalGrouping("Count Bolt");
        
        Config conf = new Config();
        LocalCluster cluster = new LocalCluster();
        
        cluster.submitTopology("CountingTopology", conf, builder.createTopology());
        
        Utils.sleep(10000); //sleep 10秒后kill掉
        cluster.killTopology("CountingTopology");
        cluster.shutdown();
    }
}

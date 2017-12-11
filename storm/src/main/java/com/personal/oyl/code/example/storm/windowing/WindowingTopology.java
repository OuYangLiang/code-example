package com.personal.oyl.code.example.storm.windowing;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseWindowedBolt.Duration;
import org.apache.storm.utils.Utils;

public class WindowingTopology {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("SeqSpout", new SeqSpout(), 1);
        builder.setBolt("OutputBolt", new OutputBolt(), 1).shuffleGrouping("SeqSpout");
        builder.setBolt("WindowingBolt", new WindowingBolt().withTumblingWindow(Duration.seconds(5))
                ).shuffleGrouping("SeqSpout");

        Config conf = new Config();
        LocalCluster cluster = new LocalCluster();

        cluster.submitTopology("WindowingTopology", conf, builder.createTopology());

        Utils.sleep(22000); // sleep 22秒后kill掉
        cluster.killTopology("WindowingTopology");
        cluster.shutdown();
    }
}

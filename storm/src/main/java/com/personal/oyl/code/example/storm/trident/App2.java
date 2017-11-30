package com.personal.oyl.code.example.storm.trident;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.testing.Split;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import com.personal.oyl.code.example.storm.trident.state.QueryWordCountDB;
import com.personal.oyl.code.example.storm.trident.state.WordCountDBFactory;
import com.personal.oyl.code.example.storm.trident.state.WordCountDBUpdater;

public class App2 {
    public static void main(String[] args) {
        @SuppressWarnings("unchecked")
        FixedBatchSpout spout = new FixedBatchSpout(new Fields("sentence"), 1, new Values("how are you"),
                new Values("nice to meet you"), new Values("what a good day"));
        spout.setCycle(false);

        TridentTopology topology = new TridentTopology();
        TridentState wordCounts = topology.newStream("spout1", spout)
                .each(new Fields("sentence"), new Split(), new Fields("word"))
                .groupBy(new Fields("word"))
                .aggregate(new Count(), new Fields("count"))
                .toStream()
                .partitionPersist(WordCountDBFactory.non_transactional, new Fields("word", "count"), new WordCountDBUpdater()).parallelismHint(1);

        LocalDRPC drpc = new LocalDRPC();
        topology.newDRPCStream("word", drpc)
                .stateQuery(wordCounts, new Fields("args"), new QueryWordCountDB(), new Fields("count"));
        
        Config conf = new Config();
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("drpc-demo", conf, topology.build());
        
        Utils.sleep(10000);
        System.out.println("DRPC RESULT: " + drpc.execute("word", "how"));
        System.out.println("DRPC RESULT: " + drpc.execute("word", "are"));
        System.out.println("DRPC RESULT: " + drpc.execute("word", "you"));
        System.out.println("DRPC RESULT: " + drpc.execute("word", "nice"));
        System.out.println("DRPC RESULT: " + drpc.execute("word", "to"));
        System.out.println("DRPC RESULT: " + drpc.execute("word", "meet"));
        System.out.println("DRPC RESULT: " + drpc.execute("word", "what"));
        System.out.println("DRPC RESULT: " + drpc.execute("word", "a"));
        System.out.println("DRPC RESULT: " + drpc.execute("word", "good"));
        System.out.println("DRPC RESULT: " + drpc.execute("word", "day"));
        
        cluster.shutdown();
        drpc.shutdown();
    }
}

package com.personal.oyl.code.example.storm.vanilla;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

@SuppressWarnings("serial")
public class ReportBolt extends BaseRichBolt {
    private OutputCollector collector;

    @Override
    public void prepare(@SuppressWarnings("rawtypes") Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String word = input.getString(0);
        Long count  = input.getLong(1);
        
        System.out.println("Word: " + word + ", Count: " + count);
        collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        
    }

}

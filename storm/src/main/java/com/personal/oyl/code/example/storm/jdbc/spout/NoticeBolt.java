package com.personal.oyl.code.example.storm.jdbc.spout;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

@SuppressWarnings("serial")
public class NoticeBolt extends BaseRichBolt {
    @SuppressWarnings("rawtypes")
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

    }

    @Override
    public void execute(Tuple input) {
        Long id = input.getLong(0);
        String name = input.getString(1);

        System.out.println("Id: " + id + ", Name: " + name);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

}

package com.personal.oyl.code.example.storm.vanilla;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

@SuppressWarnings("serial")
public class SentenceSpout extends BaseRichSpout {
    private static Long count = 1l;
    private Map<Long, Values> pending;
    private SpoutOutputCollector collector;
    private int index = 0;
    private String[] sentences = {
        "how are you", "nice to meet you", "what a good day"
    };
    
    @Override
    public void open(@SuppressWarnings("rawtypes") Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        pending = new HashMap<>();
    }

    @Override
    public void nextTuple() {
        if (index == sentences.length)
            return;
        
        Long msgId = count;
        Values value = new Values(sentences[index]);
        pending.put(msgId, value);
        this.collector.emit(value, msgId);
        count++;
        index++;
        //if (index == sentences.length)
        //    index = 0;
        Utils.sleep(5);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("sentence"));
    }

    @Override
    public void ack(Object msgId) {
        this.pending.remove(msgId);
    }

    @Override
    public void fail(Object msgId) {
        this.collector.emit(this.pending.get(msgId), msgId);
    }
}

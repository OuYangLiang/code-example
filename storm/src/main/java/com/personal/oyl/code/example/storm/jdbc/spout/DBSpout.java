package com.personal.oyl.code.example.storm.jdbc.spout;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class DBSpout extends BaseRichSpout {
    
    private static final long serialVersionUID = 1L;
    private SpoutOutputCollector collector;
    private static Long lastId = 0l;

    @SuppressWarnings("rawtypes")
    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void nextTuple() {
        
        try {
            List<Record> records = DBQuery.INSTANCE.query(lastId);
            if (null != records) {
                for (Record record : records) {
                    collector.emit(new Values(record.getId(), record.getDesc()));
                    lastId = record.getId();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("end");
        Utils.sleep(500);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("id", "name"));
    }

}

package com.personal.oyl.code.example.storm.trident.spout.transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.trident.spout.ITridentSpout;
import org.apache.storm.tuple.Fields;

public class TransactionalSpout implements ITridentSpout<Integer> {

    private static final long serialVersionUID = 1L;
    
    static List<String> list = new ArrayList<>();
    
    static {
        list.add("how are you");
        list.add("nice to meet you");
        list.add("what a good day");
    }

    @Override
    public BatchCoordinator<Integer> getCoordinator(String txStateId, @SuppressWarnings("rawtypes") Map conf, TopologyContext context) {
        return new TheBatchCoordinator();
    }

    @Override
    public Emitter<Integer> getEmitter(String txStateId, @SuppressWarnings("rawtypes") Map conf, TopologyContext context) {
        return new TheEmitter();
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

    @Override
    public Fields getOutputFields() {
        return new Fields("sentence");
    }


}

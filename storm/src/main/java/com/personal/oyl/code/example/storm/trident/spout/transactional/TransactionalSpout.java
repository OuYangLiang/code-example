package com.personal.oyl.code.example.storm.trident.spout.transactional;

import java.util.Map;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.trident.spout.ITridentSpout;
import org.apache.storm.tuple.Fields;

public class TransactionalSpout implements ITridentSpout<TheMetadata> {

    private static final long serialVersionUID = 1L;

    @Override
    public BatchCoordinator<TheMetadata> getCoordinator(String txStateId, @SuppressWarnings("rawtypes") Map conf, TopologyContext context) {
        return new TheBatchCoordinator();
    }

    @Override
    public Emitter<TheMetadata> getEmitter(String txStateId, @SuppressWarnings("rawtypes") Map conf, TopologyContext context) {
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

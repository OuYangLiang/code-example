package com.personal.oyl.code.example.storm.trident.spout.transactional;

import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.spout.ITridentSpout;
import org.apache.storm.trident.topology.TransactionAttempt;
import org.apache.storm.tuple.Values;

public class TheEmitter implements ITridentSpout.Emitter<Integer> {
    
    @Override
    public void emitBatch(TransactionAttempt tx, Integer coordinatorMeta, TridentCollector collector) {
        String sentence = TransactionalSpout.list.get(coordinatorMeta);
        collector.emit(new Values(sentence));
    }

    @Override
    public void success(TransactionAttempt tx) {
        
    }

    @Override
    public void close() {
        
    }

}

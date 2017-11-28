package com.personal.oyl.code.example.storm.trident.spout.transactional;

import java.util.ArrayList;
import java.util.List;

import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.spout.ITridentSpout;
import org.apache.storm.trident.topology.TransactionAttempt;
import org.apache.storm.tuple.Values;

public class TheEmitter implements ITridentSpout.Emitter<TheMetadata> {
    
    private static List<String> list = new ArrayList<>();
    
    static {
        list.add("you you you");
        list.add("you you you you you you you you you");
        list.add("you you you you you you");
    }

    @Override
    public void emitBatch(TransactionAttempt tx, TheMetadata coordinatorMeta, TridentCollector collector) {
        if (tx.getTransactionId() > 3) {
            return;
        }
        System.out.println(tx);
        System.out.println("Metadata in emitBatch: " + coordinatorMeta.getIndex());
        String sentence = list.get(tx.getTransactionId().intValue() - 1 );
        collector.emit(new Values(sentence));
    }

    @Override
    public void success(TransactionAttempt tx) {
        
    }

    @Override
    public void close() {
        
    }

}

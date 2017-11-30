package com.personal.oyl.code.example.storm.trident.state;

import java.util.Map;

import org.apache.storm.task.IMetricsContext;
import org.apache.storm.trident.state.State;
import org.apache.storm.trident.state.StateFactory;

public class WordCountDBFactory {
    
    public static StateFactory non_transactional = new StateFactory() {
        @Override
        public State makeState(Map conf, IMetricsContext metrics, int partitionIndex, int numPartitions) {
            return new WordCountDB();
        }
    };
    
    public static StateFactory transactional = new StateFactory() {
        @Override
        public State makeState(Map conf, IMetricsContext metrics, int partitionIndex, int numPartitions) {
            return new TransactionalWordCountDB();
        }
    };
    
    public static StateFactory opaque_transactional = new StateFactory() {
        @Override
        public State makeState(Map conf, IMetricsContext metrics, int partitionIndex, int numPartitions) {
            return new OpaqueWordCountDB();
        }
    };
}

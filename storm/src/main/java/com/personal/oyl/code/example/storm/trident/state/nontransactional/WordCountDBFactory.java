package com.personal.oyl.code.example.storm.trident.state.nontransactional;

import java.util.Map;

import org.apache.storm.task.IMetricsContext;
import org.apache.storm.trident.state.State;
import org.apache.storm.trident.state.StateFactory;

@SuppressWarnings("serial")
public class WordCountDBFactory implements StateFactory {
    @Override
    public State makeState(@SuppressWarnings("rawtypes") Map conf, IMetricsContext metrics, int partitionIndex,
            int numPartitions) {
        return new WordCountDB();
    }
}

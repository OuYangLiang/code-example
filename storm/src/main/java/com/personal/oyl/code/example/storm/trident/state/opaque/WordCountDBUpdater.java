package com.personal.oyl.code.example.storm.trident.state.opaque;

import java.util.List;

import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.state.BaseStateUpdater;
import org.apache.storm.trident.tuple.TridentTuple;

@SuppressWarnings("serial")
public class WordCountDBUpdater extends BaseStateUpdater<WordCountDB> {

    @Override
    public void updateState(WordCountDB state, List<TridentTuple> tuples, TridentCollector collector) {
        for (TridentTuple tuple : tuples) {
            String word = tuple.getString(0);
            Long count = tuple.getLong(1);
            state.setCount(word, count);
        }
    }

}

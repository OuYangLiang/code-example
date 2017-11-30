package com.personal.oyl.code.example.storm.trident.state.transactional;

import java.util.LinkedList;
import java.util.List;

import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.state.BaseStateUpdater;
import org.apache.storm.trident.tuple.TridentTuple;

@SuppressWarnings("serial")
public class WordCountDBUpdater extends BaseStateUpdater<WordCountDB> {

    @Override
    public void updateState(WordCountDB state, List<TridentTuple> tuples, TridentCollector collector) {
        List<String> words = new LinkedList<>();
        List<Long> counts  = new LinkedList<>();
        for (TridentTuple tuple : tuples) {
            words.add(tuple.getString(0));
            counts.add(tuple.getLong(1));
        }
        state.incrCount(words, counts);
    }

}

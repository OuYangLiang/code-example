package com.personal.oyl.code.example.storm.trident.state.nontransactional;

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
            Integer curr = state.getCount(word);
            state.setCount(word, null == curr ? 1 : curr + 1);
        }
    }

}

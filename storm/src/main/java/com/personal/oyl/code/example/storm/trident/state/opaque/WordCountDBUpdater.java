package com.personal.oyl.code.example.storm.trident.state.opaque;

import java.util.List;

import org.apache.storm.topology.FailedException;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.state.BaseStateUpdater;
import org.apache.storm.trident.tuple.TridentTuple;

@SuppressWarnings("serial")
public class WordCountDBUpdater extends BaseStateUpdater<WordCountDB> {

    private boolean firsttime = true;
    @Override
    public void updateState(WordCountDB state, List<TridentTuple> tuples, TridentCollector collector) {
        for (TridentTuple tuple : tuples) {
            String word = tuple.getString(0);
            Long count = tuple.getLong(1);
            
            if (count == 6l && firsttime) {
                System.out.println("exception here...");
                firsttime = false;
                throw new FailedException();
            }
            state.setCount(word, count);
        }
    }

}

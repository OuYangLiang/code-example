package com.personal.oyl.code.example.storm.trident.state.opaque;

import java.util.LinkedList;
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
        List<String> words = new LinkedList<>();
        List<Long> counts  = new LinkedList<>();
        for (TridentTuple tuple : tuples) {
            
            if (tuple.getLong(1) == 6l && firsttime) {
                System.out.println("exception here...");
                firsttime = false;
                throw new FailedException();
            }
            
            words.add(tuple.getString(0));
            counts.add(tuple.getLong(1));
        }
        state.incrCount(words, counts);
    }

}

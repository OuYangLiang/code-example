package com.personal.oyl.code.example.storm.trident.state;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.storm.trident.state.State;

public class WordCountDB implements State {
    private Map<String, Long> inner = new HashMap<>();
    
    @Override
    public void beginCommit(Long txid) {
        System.out.println("State update begin, for batch: " + txid);
    }

    @Override
    public void commit(Long txid) {
        System.out.println("State update committed, for batch: " + txid);
    }

    public void incrCount(List<String> words, List<Long> counts) {
        int size = words.size();
        for (int i = 0; i < size; i++) {
            String word = words.get(i);
            Long count = counts.get(i);
            Long prev = inner.get(word);
            if (null == prev) {
                inner.put(word, 1l);
            } else {
                inner.put(word, prev + count);
            }
        }
    }
    
    public List<Long> getCount(List<String> words) {
        List<Long> list = new LinkedList<>();
        for (String word : words) {
            list.add(null == inner.get(word) ? 0 : inner.get(word));
        }
        return list;
    }
}

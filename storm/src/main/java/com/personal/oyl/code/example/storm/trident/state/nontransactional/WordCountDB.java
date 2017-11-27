package com.personal.oyl.code.example.storm.trident.state.nontransactional;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.trident.state.State;

public class WordCountDB implements State {

    @Override
    public void beginCommit(Long txid) {
        
    }

    @Override
    public void commit(Long txid) {
        
    }


    private Map<String, Integer> inner = new HashMap<>();
    public void setCount(String word, Integer count) {
        inner.put(word, count);
    }
    
    public int getCount(String word) {
        return null == inner.get(word) ? 0 : inner.get(word);
    }
}

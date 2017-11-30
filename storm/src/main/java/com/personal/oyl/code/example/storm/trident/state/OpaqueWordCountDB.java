package com.personal.oyl.code.example.storm.trident.state;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.storm.trident.state.OpaqueValue;
import org.apache.storm.trident.state.State;

public class OpaqueWordCountDB extends WordCountDB implements State {
    
    private Long currTxid;

    @Override
    public void beginCommit(Long txid) {
        currTxid = txid;
        System.out.println("State update begin, for batch: " + txid);
    }

    @Override
    public void commit(Long txid) {
        currTxid = null;
        System.out.println("State update committed, for batch: " + txid);
    }


    private Map<String, OpaqueValue<Long>> inner = new HashMap<>();
    public void incrCount(List<String> words, List<Long> counts) {
        int size = words.size();
        for (int i = 0; i < size; i++) {
            String word = words.get(i);
            Long count = counts.get(i);
            OpaqueValue<Long> transVal = inner.get(word);
            if (null == transVal) {
                inner.put(word, new OpaqueValue<>(currTxid.longValue(), count));
            } else if (currTxid > transVal.getCurrTxid()) {
                Long prev = transVal.getCurr();
                Long curr = prev + count;
                inner.put(word, new OpaqueValue<>(currTxid.longValue(), curr, prev));
            } else if (currTxid == transVal.getCurrTxid()) {
                Long prev = transVal.getPrev();
                Long curr = prev + count;
                inner.put(word, new OpaqueValue<>(currTxid.longValue(), curr, prev));
            } else if (currTxid < transVal.getCurrTxid()) {
                throw new RuntimeException("impossible case: currTxid < transVal.getCurrTxid()...");
            }
        }
    }
    
    public List<Long> getCount(List<String> words) {
        List<Long> list = new LinkedList<>();
        for (String word : words) {
            OpaqueValue<Long> transVal = inner.get(word);
            if (null == transVal) {
                list.add(Long.valueOf(0l));
            }
            list.add(transVal.getCurr());
        }
        return list;
    }
}

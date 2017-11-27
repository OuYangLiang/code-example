package com.personal.oyl.code.example.storm.trident.state.opaque;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.trident.state.OpaqueValue;
import org.apache.storm.trident.state.State;

public class WordCountDB implements State {
    
    private Long currTxid;

    @Override
    public void beginCommit(Long txid) {
        currTxid = txid;
        System.out.println("Start batch with id: " + txid);
    }

    @Override
    public void commit(Long txid) {
        currTxid = null;
        System.out.println("End batch with id: " + txid);
    }


    private Map<String, OpaqueValue<Long>> inner = new HashMap<>();
    public void setCount(String word, Long count) {
        System.out.println(word + count);
        
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
    
    public long getCount(String word) {
        OpaqueValue<Long> transVal = inner.get(word);
        if (null == transVal) {
            return 0;
        }
        return transVal.getCurr();
    }
}

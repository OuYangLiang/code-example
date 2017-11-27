package com.personal.oyl.code.example.storm.trident.state.transactional;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.trident.state.State;
import org.apache.storm.trident.state.TransactionalValue;

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


    private Map<String, TransactionalValue<Long>> inner = new HashMap<>();
    public void setCount(String word, Long count) {
        System.out.println(word + count);
        
        TransactionalValue<Long> transVal = inner.get(word);
        if (null == transVal) {
            inner.put(word, new TransactionalValue<>(currTxid.longValue(), count));
        } else if (currTxid > transVal.getTxid()) {
            Long prev = transVal.getVal();
            Long curr = prev + count;
            inner.put(word, new TransactionalValue<>(currTxid.longValue(), curr));
        } else if (currTxid == transVal.getTxid()) {
            System.out.println("Iginre...");
        } else if (currTxid < transVal.getTxid()) {
            throw new RuntimeException("impossible case: currTxid < transVal.getTxid()...");
        }
    }
    
    public long getCount(String word) {
        TransactionalValue<Long> transVal = inner.get(word);
        if (null == transVal) {
            return 0;
        }
        return transVal.getVal();
    }
}

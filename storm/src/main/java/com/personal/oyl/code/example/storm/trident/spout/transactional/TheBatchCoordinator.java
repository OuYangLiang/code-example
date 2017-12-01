package com.personal.oyl.code.example.storm.trident.spout.transactional;

import org.apache.storm.trident.spout.ITridentSpout;

public class TheBatchCoordinator implements ITridentSpout.BatchCoordinator<Integer> {

    @Override
    public Integer initializeTransaction(long txid, Integer prevMetadata, Integer currMetadata) {
        System.out.println("Init transaction: " + txid);
        
        if (txid == 1l) {
            return 0;
        }
        
        return prevMetadata + 1;
    }

    @Override
    public void success(long txid) {
        
    }

    @Override
    public boolean isReady(long txid) {
        if (txid <= TransactionalSpout.list.size()) {
            return true;
        }
        return false;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        
    }

}

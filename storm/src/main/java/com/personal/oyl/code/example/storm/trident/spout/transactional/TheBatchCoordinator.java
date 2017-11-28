package com.personal.oyl.code.example.storm.trident.spout.transactional;

import org.apache.storm.trident.spout.ITridentSpout;

public class TheBatchCoordinator implements ITridentSpout.BatchCoordinator<TheMetadata> {

    @Override
    public TheMetadata initializeTransaction(long txid, TheMetadata prevMetadata, TheMetadata currMetadata) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void success(long txid) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isReady(long txid) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        
    }

}

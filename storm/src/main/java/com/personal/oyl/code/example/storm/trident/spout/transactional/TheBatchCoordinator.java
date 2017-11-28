package com.personal.oyl.code.example.storm.trident.spout.transactional;

import org.apache.storm.trident.spout.ITridentSpout;

public class TheBatchCoordinator implements ITridentSpout.BatchCoordinator<TheMetadata> {

    @Override
    public TheMetadata initializeTransaction(long txid, TheMetadata prevMetadata, TheMetadata currMetadata) {
        System.out.println("Init transaction: " + txid);
        
        if (null != prevMetadata)
            System.out.println("Prev Metadata" + prevMetadata.getIndex());
        if (null != currMetadata)
            System.out.println("Curr Metadata" + currMetadata.getIndex());
        
        if (txid <= 3) {
            TheMetadata rlt = new TheMetadata();
            rlt.setIndex(Long.valueOf(txid).intValue() -1);
            return rlt;
        }
        return null;
    }

    @Override
    public void success(long txid) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isReady(long txid) {
        if (txid <= 3) {
            return true;
        }
        
        return false;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        
    }

}

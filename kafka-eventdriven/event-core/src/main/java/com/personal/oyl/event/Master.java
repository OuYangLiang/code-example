package com.personal.oyl.event;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Master {
    
    private static final Logger log = LoggerFactory.getLogger(Master.class);
    
    private ZooKeeper zk;
    private Configuration cfg;
    private SimpleLock lock;
    
    private Watcher masterWatcher = (event) -> {
        if (event.getType().equals(EventType.NodeChildrenChanged)) {
            try {
                Master.this.onWorkerChange();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    };
    
    public void start() throws IOException, InterruptedException, KeeperException {
        String uuid = UUID.randomUUID().toString();
        
        CountDownLatch latch = new CountDownLatch(1);
        
        zk = new ZooKeeper(cfg.getZkAddrs(), cfg.getSessionTimeout(), new Watcher() {

            @Override
            public void process(WatchedEvent event) {
                if (event.getState().equals(KeeperState.Expired)) {
                    try {
                        Master.this.close();
                        Master.this.start();
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                    
                }
                
                if (event.getState().equals(KeeperState.SyncConnected)) {
                    latch.countDown();
                }
            }
            
        });
        
        latch.await();
        
        lock = new SimpleLock(zk);
        lock.lock(uuid, cfg.getMasterNode());
        log.info("Now it is the master server...");
        // do what it should do as a master...
    }
    
    private void close() {
        if (null != zk) {
            try {
                zk.close();
                zk = null;
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
    
    private List<String> getChild(String znode, Watcher watcher) throws KeeperException, InterruptedException {
        try{
            if (null == watcher) {
                return zk.getChildren(cfg.getNamespace() + znode, false);
            } else {
                return zk.getChildren(cfg.getNamespace() + znode, watcher);
            }
        } catch(KeeperException e){
            if (e.code().equals(KeeperException.Code.CONNECTIONLOSS)) {
                if (null == watcher) {
                    return zk.getChildren(cfg.getNamespace() + znode, false);
                } else {
                    return zk.getChildren(cfg.getNamespace() + znode, watcher);
                }
            } else {
                throw e;
            }
        }
    }
    
    private String getContent(String znode, Watcher watcher) throws KeeperException, InterruptedException {
        Stat stat = new Stat();
        try{
            byte[] source = zk.getData(znode, watcher, stat);
            return null == source ? null : new String(source);
        } catch(KeeperException e){
            if (e.code().equals(KeeperException.Code.CONNECTIONLOSS)) {
                return this.getContent(znode, watcher);
            } else {
                throw e;
            }
        }
    }
    
    private void onWorkerChange() throws KeeperException, InterruptedException {
        List<String> workerList = this.getChild(cfg.getWorkerNode(), masterWatcher);
        for (String worker : workerList) {
            
        }
    }
}

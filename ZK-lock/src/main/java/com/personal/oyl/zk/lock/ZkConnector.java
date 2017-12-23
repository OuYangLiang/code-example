/*
 * File Name:ZkConnector.java
 * Author:ouyangliang2
 * Date:2017年7月24日
 * Copyright (C) 2006-2017
 */

package com.personal.oyl.zk.lock;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

/**
 * @author:ouyangliang2
 */
public class ZkConnector implements Watcher {

    protected ZooKeeper zk;
    private CountDownLatch latch = new CountDownLatch(1);

    public void connect(String host) throws IOException, InterruptedException {
        zk = new ZooKeeper(host, 10000, this);
        latch.await();
    }

    public void close() throws InterruptedException {
        if(null != zk){
            zk.close();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        if(event.getState() == KeeperState.SyncConnected){
            latch.countDown();
        }
    }

    public ZooKeeper getZk() {
        return zk;
    }

}

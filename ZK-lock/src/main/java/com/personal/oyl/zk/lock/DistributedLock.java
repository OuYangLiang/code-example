/*
 * File Name:BetterLock.java
 * Author:ouyangliang2
 * Date:2017年7月25日
 * Copyright (C) 2006-2017 Tuniu All rights reserved
 */
 
package com.personal.oyl.zk.lock;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.data.Stat;

/**
 * @author:ouyangliang2
 */
public class DistributedLock {
    private static final String SEPARATOR = "/";
    private static final String NODE_NAME = "lock-";
    
    private ZooKeeper zk;
    private String root;
    
    public DistributedLock(ZooKeeper zk, String root) {
        this.zk = zk;
        this.root = root.endsWith(SEPARATOR) ? root : root + SEPARATOR;
    }
    
    /**
     * 1. Call create( ) with a pathname of "_locknode_/lock-" and the sequence and ephemeral flags set.<br>
     * 2. Call getChildren( ) on the lock node without setting the watch flag (this is important to avoid the herd effect).<br>
     * 3. If the pathname created in step 1 has the lowest sequence number suffix, the client has the lock and the client exits the protocol.<br>
     * 4. The client calls exists( ) with the watch flag set on the path in the lock directory with the next lowest sequence number.<br>
     * 5. if exists( ) returns false, go to step 2. Otherwise, wait for a notification for the pathname from the previous step before going to step 2.
     *
     * @param clientId
     * @param resource
     * @throws KeeperException 
     * @throws InterruptedException 
     */
    public String lock(String clientId, String resource) throws KeeperException, InterruptedException {
        this.ensureResource(clientId, resource);
        
        try{
            String path = zk.create(root + resource + SEPARATOR + NODE_NAME, clientId.getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            
            this.waitUntilLocked(path, clientId, resource);
            return path;
        } catch(KeeperException e){
            if (e.code().equals(KeeperException.Code.CONNECTIONLOSS)) {
                return this.lockOnConnectionLoss(clientId, resource);
            } else {
                throw e;
            }
        }
    }
    
    private String lockOnConnectionLoss(String clientId, String resource) throws KeeperException, InterruptedException {
        List<String> pathes = this.getChildren(resource);
        
        String myPath = findMyNodeOnConnectionLoss(pathes, clientId);
        
        if (null == myPath) {
            return this.lock(clientId, resource);
        }
        
        this.waitUntilLocked(myPath, clientId, resource);
        return myPath;
    }
    
    private void waitUntilLocked(String myPath, String clientId, String resource)
            throws KeeperException, InterruptedException {
        while (true) {
            List<String> pathes = this.getChildren(resource);
            if (this.isLocked(myPath, pathes)) {
                return;
            } else {
                String target = this.findWatchPath(myPath, pathes, resource);
                this.watchAndWait(target);
            }
        }
    }
    
    private void watchAndWait(String target)
            throws KeeperException, InterruptedException {
        Semaphore s = new Semaphore(0);
        try{
            Stat stat = zk.exists(target, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getType().equals(EventType.NodeDeleted)) {
                        s.release();
                    }
                }
            });
            
            if (null != stat) {
                s.acquire();
            }
        } catch(KeeperException e){
            if (e.code().equals(KeeperException.Code.CONNECTIONLOSS)) {
                this.watchAndWait(target);
                return;
            } else {
                throw e;
            }
        }
    }
    
    private int parseSequence(String path) {
        return Integer.parseInt(path.substring(path.length() - 10));
    }
    
    private boolean isLocked(String myPath, List<String> pathes) {
        int mySeq = this.parseSequence(myPath);
        int firstSeq = this.parseSequence(pathes.get(0));
        
        return mySeq == firstSeq;
    }
    
    private String findWatchPath(String myPath, List<String> pathes, String resource) {
        String prefix = root + resource + SEPARATOR;
        int start = prefix.length();
        
        String path;
        for (int i = pathes.size() -1; i >= 0; i--) {
            path = pathes.get(i);
            if (path.equals(myPath.substring(start))) {
                return prefix + pathes.get(i-1);
            }
        }
        
        throw new IllegalStateException();
    }
    
    private String findMyNodeOnConnectionLoss(List<String> pathes, String clientId)
            throws KeeperException, InterruptedException {
        if (null == pathes || pathes.isEmpty()) {
            return null;
        }
        
        String path;
        for (int i = pathes.size() -1; i >= 0; i--) {
            path = pathes.get(i);
            if (this.isClientIdMatch(path, clientId)) {
                return path;
            }
        }
        
        return null;
    }
    
    private boolean isClientIdMatch(String path, String clientId) throws KeeperException, InterruptedException {
        
        try{
            Stat stat = new Stat();
            byte[] data = zk.getData(path, false, stat);
            if (clientId.equals(new String(data))) {
                return true;
            }
            
            return false;
        } catch(KeeperException e){
            if (e.code().equals(KeeperException.Code.NONODE)) {
                return false;
            } else if (e.code().equals(KeeperException.Code.CONNECTIONLOSS)) {
                return this.isClientIdMatch(path, clientId);
            } else {
                throw e;
            }
        }
    }
    
    private List<String> getChildren(String resource) throws KeeperException, InterruptedException {
        try{
            List<String> rlt = zk.getChildren(root + resource, false);
            
            Collections.sort(rlt, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    int i1 = DistributedLock.this.parseSequence(o1);
                    int i2 = DistributedLock.this.parseSequence(o2);
                    return i1 - i2;
                }
            });
            
            return rlt;
        } catch(KeeperException e){
            if (e.code().equals(KeeperException.Code.CONNECTIONLOSS)) {
                return this.getChildren(resource);
            } else {
                throw e;
            }
        }
    }
    
    public void release(String path) throws InterruptedException, KeeperException {
        try{
            zk.delete(path, -1);
        }
        catch(KeeperException e){
            if (e.code().equals(KeeperException.Code.CONNECTIONLOSS)) {
                this.release(path);
            } else {
                throw e;
            }
        }
    }
    
    private void ensureResource(String clientId, String resource) throws KeeperException, InterruptedException {
        
        Stat stat = null;
        try{
            stat = zk.exists(root + resource, false);
        } catch(KeeperException e){
            if (e.code().equals(KeeperException.Code.CONNECTIONLOSS)) {
                this.ensureResource(clientId, resource);
            } else {
                throw e;
            }
        }
        
        if (null != stat) {
            return;
        }
        
        try {
            zk.create(root + resource, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            if (e.code().equals(KeeperException.Code.CONNECTIONLOSS)) {
                this.ensureResource(clientId, resource);
            } else if (e.code().equals(KeeperException.Code.NODEEXISTS)) {
                return;
            } else {
                throw e;
            }
        }
    }
}

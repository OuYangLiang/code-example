/*
 * File Name:ReadWriteLock.java
 * Author:ouyangliang2
 * Date:2017年7月28日
 * Copyright (C) 2006-2017
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
public class ReadWriteLock {
    private static final String SEPARATOR = "/";
    private static final String READ_NODE_NAME = "readlock-";
    private static final String WRITE_NODE_NAME = "writelock-";
    
    private ZooKeeper zk;
    private String root;
    
    public ReadWriteLock(ZooKeeper zk, String root) {
        this.zk = zk;
        this.root = root.endsWith(SEPARATOR) ? root : root + SEPARATOR;
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
    
    public String lockForRead(String clientId, String resource)
            throws KeeperException, InterruptedException {
        this.ensureResource(clientId, resource);
        
        try{
            String path = zk.create(root + resource + SEPARATOR + READ_NODE_NAME, clientId.getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            
            this.waitUntilLocked(path, clientId, resource, LockType.READ);
            return path;
        }
        catch(KeeperException e){
            if (e.code().equals(KeeperException.Code.CONNECTIONLOSS)) {
                return this.lockForReadOnConnectionLoss(clientId, resource);
            } else {
                throw e;
            }
        }
    }
    
    public String lockForWrite(String clientId, String resource)
            throws KeeperException, InterruptedException {
        this.ensureResource(clientId, resource);
        
        try{
            String path = zk.create(root + resource + SEPARATOR + WRITE_NODE_NAME, clientId.getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            
            this.waitUntilLocked(path, clientId, resource, LockType.WRITE);
            return path;
        }
        catch(KeeperException e){
            if (e.code().equals(KeeperException.Code.CONNECTIONLOSS)) {
                return this.lockForWriteOnConnectionLoss(clientId, resource);
            } else {
                throw e;
            }
        }
    }
    
    private void waitUntilLocked(String myPath, String clientId, String resource, LockType type)
            throws KeeperException, InterruptedException {
        while (true) {
            List<String> pathes = this.getChildren(resource);
            
            if (LockType.READ.equals(type)) {
                if (this.isLockedForRead(myPath, pathes)) {
                    return;
                } else {
                    String target = this.findWatchPathForRead(myPath, pathes, resource);
                    this.watchAndWait(target, clientId, resource);
                }
            } else if (LockType.WRITE.equals(type)) {
                if (this.isLockedForWrite(myPath, pathes)) {
                    return;
                } else {
                    String target = this.findWatchPathForWrite(myPath, pathes, resource);
                    this.watchAndWait(target, clientId, resource);
                }
            }
        }
    }
    
    private void watchAndWait(String target, String clientId, String resource)
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
            
            if (null == stat) {
                return;
            }
            
            try {
                s.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        catch(KeeperException e){
            if (e.code().equals(KeeperException.Code.CONNECTIONLOSS)) {
                this.watchAndWait(target, clientId, resource);
                return;
            } else {
                throw e;
            }
        }
    }
    
    private String findWatchPathForWrite(String myPath, List<String> pathes, String resource) {
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
    
    private String findWatchPathForRead(String myPath, List<String> pathes, String resource) {
        String prefix = root + resource + SEPARATOR;
        int start = prefix.length();
        
        String path;
        for (int i = pathes.size() -1; i >= 0; i--) {
            path = pathes.get(i);
            if (path.equals(myPath.substring(start))) {
                
                for (int j = i - 1; j >= 0; j--) {
                    path = pathes.get(j);
                    if (path.startsWith(WRITE_NODE_NAME)) {
                        return prefix + path;
                    }
                }
            }
        }
        
        throw new IllegalStateException();
    }
    
    private boolean isLockedForRead(String myPath, List<String> pathes) {
        int mySeq = this.parseSequence(myPath);
        
        for (String path : pathes) {
            if (path.startsWith(WRITE_NODE_NAME) && this.parseSequence(path) < mySeq) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean isLockedForWrite(String myPath, List<String> pathes) {
        
        int mySeq = this.parseSequence(myPath);
        int firstSeq = this.parseSequence(pathes.get(0));
        
        return mySeq == firstSeq;
    }
    
    private String lockForReadOnConnectionLoss(String clientId, String resource) throws KeeperException, InterruptedException {
        List<String> pathes = this.getChildren(resource);
        
        String myPath = findMyNodeOnConnectionLoss(pathes, clientId);
        
        if (null == myPath) {
            return this.lockForRead(clientId, resource);
        }
        
        this.waitUntilLocked(myPath, clientId, resource, LockType.READ);
        return myPath;
    }
    
    private String lockForWriteOnConnectionLoss(String clientId, String resource) throws KeeperException, InterruptedException {
        List<String> pathes = this.getChildren(resource);
        
        String myPath = findMyNodeOnConnectionLoss(pathes, clientId);
        
        if (null == myPath) {
            return this.lockForWrite(clientId, resource);
        }
        
        this.waitUntilLocked(myPath, clientId, resource, LockType.WRITE);
        return myPath;
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
        }
        catch(KeeperException e){
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
                    int i1 = ReadWriteLock.this.parseSequence(o1);
                    int i2 = ReadWriteLock.this.parseSequence(o2);
                    return i1 - i2;
                }
                
            });
            
            return rlt;
        }
        catch(KeeperException e){
            if (e.code().equals(KeeperException.Code.CONNECTIONLOSS)) {
                return this.getChildren(resource);
            } else {
                throw e;
            }
        }
    }
    
    private int parseSequence(String path) {
        return Integer.parseInt(path.substring(path.length() - 10));
    }
    
    private void ensureResource(String clientId, String resource) throws KeeperException, InterruptedException {
        
        Stat stat = null;
        try{
            stat = zk.exists(root + resource, false);
        }
        catch(KeeperException e){
            if (e.code().equals(KeeperException.Code.CONNECTIONLOSS)) {
                this.ensureResource(clientId, resource);
            }
            else {
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
            }
            else {
                throw e;
            }
        }
    }
    
    private static enum LockType {
        READ,
        WRITE;
    };
}

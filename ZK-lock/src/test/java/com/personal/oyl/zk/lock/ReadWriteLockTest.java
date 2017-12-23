package com.personal.oyl.zk.lock;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.KeeperException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ReadWriteLockTest extends TestCase
{
    public static int result = 0;
    
    public ReadWriteLockTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( ReadWriteLockTest.class );
    }

    public void testWriteLock() throws InterruptedException, IOException
    {
        ZkConnector connector = new ZkConnector();
        connector.connect("127.0.0.1:2181");
        
        ReadWriteLock lock = new ReadWriteLock(connector.getZk(), "/better-lock/");
        
        ExecutorService service = null;
        
        for (int j = 1; j <= 5; j++) {
            service = new ThreadPoolExecutor(10, 100, 100, TimeUnit.SECONDS, 
                    new ArrayBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.CallerRunsPolicy() );
            
            result = 0;
            for (int i = 1; i <= 100; i++) {
                service.submit(new TaskForWriteLock(lock));
            }
            
            service.shutdown();
            service.awaitTermination(1, TimeUnit.HOURS);
            
            assertEquals(100, result);
            
        }
        
        connector.close();
    }
    
    public void testReadLock() throws InterruptedException, IOException, KeeperException {
        String RES = "RW";
        
        ZkConnector connector = new ZkConnector();
        connector.connect("127.0.0.1:2181");
        
        ReadWriteLock lock = new ReadWriteLock(connector.getZk(), "/better-lock/");
        
        String path1 = lock.lockForRead(Integer.toString(1), RES);
        String path2 = lock.lockForRead(Integer.toString(2), RES);
        String path3 = lock.lockForRead(Integer.toString(3), RES);
        
        lock.release(path1);
        lock.release(path2);
        lock.release(path3);
        
        connector.close();
    }
    
    public void testReadWriteLock1() throws IOException, InterruptedException, KeeperException {
        String RES = "RW";
        
        ZkConnector connector = new ZkConnector();
        connector.connect("127.0.0.1:2181");
        
        ReadWriteLock lock = new ReadWriteLock(connector.getZk(), "/better-lock/");
        
        String path1 = lock.lockForRead(Integer.toString(1), RES);
        
        long start = System.currentTimeMillis();
        new Thread() {
            public void run() {
                try{
                    TimeUnit.SECONDS.sleep(10);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                
                try{
                    lock.release(path1);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                catch(KeeperException e){
                    e.printStackTrace();
                }
            }
        }.start();
        
        String path2 = lock.lockForWrite(Integer.toString(1), RES);
        
        long end = System.currentTimeMillis();
        
        assertTrue ((end - start) >= 10000);
        
        lock.release(path2);
        
        connector.close();
    }
    
    public void testReadWriteLock2() throws IOException, InterruptedException, KeeperException {
        String RES = "RW";
        
        ZkConnector connector = new ZkConnector();
        connector.connect("127.0.0.1:2181");
        
        ReadWriteLock lock = new ReadWriteLock(connector.getZk(), "/better-lock/");
        
        String path1 = lock.lockForWrite(Integer.toString(1), RES);
        
        long start = System.currentTimeMillis();
        new Thread() {
            public void run() {
                try{
                    TimeUnit.SECONDS.sleep(10);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                
                try{
                    lock.release(path1);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                catch(KeeperException e){
                    e.printStackTrace();
                }
            }
        }.start();
        
        String path2 = lock.lockForRead(Integer.toString(1), RES);
        
        long end = System.currentTimeMillis();
        
        assertTrue ((end - start) >= 10000);
        
        lock.release(path2);
        
        connector.close();
    }
    
}


class TaskForWriteLock implements Runnable {
    
    private static final String RES = "RW";
    private static int counter = 1;
    private static Random r = new Random();
    
    private int id = counter++;
    private ReadWriteLock lock;
    
    public TaskForWriteLock(ReadWriteLock lock) {
        this.lock = lock;
    }
    
    @Override
    public void run() {
        
        String path = null;
        try{
            path = lock.lockForWrite(Integer.toString(id), RES);
        }
        catch(KeeperException e1){
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        catch(InterruptedException e1){
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        int i = ReadWriteLockTest.result;
        
        try{
            TimeUnit.MILLISECONDS.sleep(r.nextInt(100));
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        
        
        ReadWriteLockTest.result = i + 1;
        
        try{
            lock.release(path);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        catch(KeeperException e){
            e.printStackTrace();
        }
    }
    
}
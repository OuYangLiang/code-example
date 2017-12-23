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
public class SimpleLockTest extends TestCase
{
    public static int result = 0;
    
    public SimpleLockTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( SimpleLockTest.class );
    }

    public void testLock() throws InterruptedException, IOException
    {
        ZkConnector connector = new ZkConnector();
        connector.connect("127.0.0.1:2181");
        
        SimpleLock lock = new SimpleLock(connector.getZk(), "/lock/");
        
        ExecutorService service = null;
        
        for (int j = 1; j <= 5; j++) {
            service = new ThreadPoolExecutor(10, 100, 100, TimeUnit.SECONDS, 
                    new ArrayBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.CallerRunsPolicy() );
            
            result = 0;
            for (int i = 1; i <= 100; i++) {
                service.submit(new TaskForLock(lock));
            }
            
            service.shutdown();
            service.awaitTermination(1, TimeUnit.HOURS);
            
            assertEquals(100, result);
            
        }
        
        connector.close();
    }
    
    public void testTryLock() throws InterruptedException, IOException
    {
        ZkConnector connector = new ZkConnector();
        connector.connect("127.0.0.1:2181");
        
        SimpleLock lock = new SimpleLock(connector.getZk(), "/lock");
        
        for (int j = 1; j <= 5; j++) {
            ExecutorService service = new ThreadPoolExecutor(10, 100, 100, TimeUnit.SECONDS, 
                    new ArrayBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.CallerRunsPolicy() );
            
            result = 0;
            for (int i = 1; i <= 100; i++) {
                service.submit(new TaskForTryLock(lock));
            }
            
            service.shutdown();
            service.awaitTermination(1, TimeUnit.HOURS);
            
            assertEquals(100, result);
        }
        
        connector.close();
    }
    
}

class TaskForTryLock implements Runnable {
    
    private static final String RES = "XXX";
    private static int counter = 1;
    private static Random r = new Random(5);
    
    private int id = counter++;
    private SimpleLock lock;
    
    public TaskForTryLock(SimpleLock lock) {
        this.lock = lock;
    }
    
    @Override
    public void run() {
        try {
            while(!lock.tryLock(Integer.toString(id), RES)) {
                
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        int i = SimpleLockTest.result;
        
        try{
            TimeUnit.MILLISECONDS.sleep(r.nextInt(100));
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        
        
        SimpleLockTest.result = i + 1;
        
        try {
            lock.release(Integer.toString(id), RES);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}

class TaskForLock implements Runnable {
    
    private static final String RES = "YYY";
    private static int counter = 1;
    private static Random r = new Random();
    
    private int id = counter++;
    private SimpleLock lock;
    
    public TaskForLock(SimpleLock lock) {
        this.lock = lock;
    }
    
    @Override
    public void run() {
        
        try {
            lock.lock(Integer.toString(id), RES);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        int i = SimpleLockTest.result;
        
        try{
            TimeUnit.MILLISECONDS.sleep(r.nextInt(100));
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        
        
        SimpleLockTest.result = i + 1;
        
        try {
            lock.release(Integer.toString(id), RES);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
}
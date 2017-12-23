package com.personal.oyl.zk.lock;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class DistributedLockTest extends TestCase
{
    public static int result = 0;
    
    public DistributedLockTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( DistributedLockTest.class );
    }

    
    public void testDistributedLock() throws InterruptedException, IOException
    {
        ZkConnector connector = new ZkConnector();
        connector.connect("127.0.0.1:2181");
        
        DistributedLock lock = new DistributedLock(connector.getZk(), "/better-lock");
        
        for (int j = 1; j <= 5; j++) {
            ExecutorService service = new ThreadPoolExecutor(100, 100, 100, TimeUnit.SECONDS, 
                    new ArrayBlockingQueue<Runnable>(100), new ThreadPoolExecutor.CallerRunsPolicy() );
            
            result = 0;
            for (int i = 1; i <= 100; i++) {
                service.submit(new TaskForBetterLock(lock));
            }
            
            service.shutdown();
            service.awaitTermination(1, TimeUnit.HOURS);
            
            assertEquals(100, result);
        }
        
        connector.close();
    }
}

class TaskForBetterLock implements Runnable {
    
    private static final String RES = "ZZZ";
    private static int counter = 1;
    private static Random r = new Random();
    
    private int id = counter++;
    private DistributedLock lock;
    
    public TaskForBetterLock(DistributedLock lock) {
        this.lock = lock;
    }
    
    @Override
    public void run() {
        
        try {
            String path = lock.lock(Integer.toString(id), RES);
            
            int i = DistributedLockTest.result;
            
            try{
                TimeUnit.MILLISECONDS.sleep(r.nextInt(100));
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            
            
            DistributedLockTest.result = i + 1;
            
            lock.release(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}

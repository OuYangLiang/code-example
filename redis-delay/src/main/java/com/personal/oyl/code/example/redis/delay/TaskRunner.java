package com.personal.oyl.code.example.redis.delay;


import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author OuYang Liang
 * @since 2019-09-24
 */
public class TaskRunner implements Runnable {

    private String topic;
    private int partition;
    private RedisUtil redisUtil;

    public TaskRunner(String topic, int partition, RedisUtil redisUtil) {
        this.topic = topic;
        this.partition = partition;
        this.redisUtil = redisUtil;
    }

    @Override
    public void run() {

        MessageProcessor processor = ProcessorConfig.instance().getProcessor(this.topic);

        if (null == processor) {
            throw new RuntimeException("Processor not found"); // TODO
        }

        while (!Thread.currentThread().isInterrupted()) {

            Date now = new Date();
            long timestamp = TimeUtil.getInstance().format(now);

            List<String> msgs = redisUtil.query(topic, partition, timestamp);
            if (null != msgs && !msgs.isEmpty()) {
                for (String msg : msgs) {
                    try {
                        processor.process(msg);
                    } catch (Exception e) {
                        // TODO
                    }
                }
            }

            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}

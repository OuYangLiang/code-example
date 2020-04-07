package com.personal.oyl.code.example.redis.delay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class App {

    public static final String TEST_TOPIC = "test-topic";

    public static void main(String[] args) throws ParseException, InterruptedException {

        RedisUtil redisUtil = new RedisUtil();

        ProcessorConfig.instance().setProcessor(TEST_TOPIC, System.out::println);

        new Thread(new TaskRunner(TEST_TOPIC, 0, redisUtil)).start();

        redisUtil.push(TEST_TOPIC, 0, "Test Message", format("2020-04-07 15:10:30"));

        TimeUnit.MINUTES.sleep(100);
    }

    private static long format(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(time).getTime();
    }
}
